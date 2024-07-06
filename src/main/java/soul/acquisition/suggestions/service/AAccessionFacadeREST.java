/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import soul.acquisition.suggestions.AAccession;
import soul.acquisition.suggestions.AInvoice;
import soul.acquisition.suggestions.ARequest;
import soul.catalogue.Biblidetails;
import soul.catalogue.BiblidetailsLocation;
import soul.catalogue.BiblidetailsPK;
import soul.catalogue.Location;
import soul.catalogue.LocationPK;
import soul.catalogue.service.BiblidetailsFacadeREST;
import soul.catalogue.service.BiblidetailsLocationFacadeREST;
import soul.catalogue.service.LocationFacadeREST;
import soul.system_setting.AccessionCtype;
import soul.system_setting.service.AccessionCtypeFacadeREST;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.acquisition.suggestions.aaccession")
public class AAccessionFacadeREST extends AbstractFacade<AAccession> {

    @EJB
    private AccessionCtypeFacadeREST accessionCtypeFacadeREST;
    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private AInvoiceFacadeREST aInvoiceFacadeREST;
    @EJB
    private BiblidetailsFacadeREST bibdetailsFacadeREST;
    @EJB
    private BiblidetailsLocationFacadeREST bibdetailsLocFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output = "", autoAssignedAccNo = "";
    AAccession accession = new AAccession();
    AInvoice invoice = new AInvoice();
    List<Location> locationList = new ArrayList<>();
    List<AInvoice> invoiceList = new ArrayList<>();
    List<String> alreadyExistingAccNo = new ArrayList<>();
    JsonArrayBuilder alreadyExistingArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder differentTitleArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder accessionedArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder availableTitleBuilder = Json.createArrayBuilder();
    Biblidetails biblidetails = new Biblidetails();
    JsonObject jsonObject;
    String[] accIds, accNos;

    public AAccessionFacadeREST() {
        super(AAccession.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AAccession entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(AAccession entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public AAccession find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AAccession> findAll() {
        return super.findAll();
    }

    @GET
    @Path("by/{namedQuery}/{attrValues}")
    @Produces({"application/xml", "application/json"})
    public List<AAccession> findBy(@PathParam("namedQuery") String query, @PathParam("attrValues") String attrValues) {
        String[] valueString = attrValues.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "findByAccnoNULL":
                return super.findBy("AAccession." + query);
        }
        return super.findBy("AOrder." + query, valueList);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AAccession> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // added manually
    @GET
    @Path("retrieveAll/{form}/{accept}/{collectionType}")
   // public Response retrieveAll(@QueryParam("form") String form, @QueryParam("accept") String accept,
     //       @QueryParam("collectionType") String collectionType) {
    public Response retrieveAll(@PathParam("form") String form, @PathParam("accept") String accept,
            @PathParam("collectionType") String collectionType) {    
        Response.ResponseBuilder responseBuilder = Response.status(200);
        List<AAccession> acclist;
        AccessionCtype accessionCtype;
        if (accept.equals("XML")) {
            if (form.equals("AccessioningProcess")) {
                acclist = findBy("findByAccnoNULL", "null");
                GenericEntity<List<AAccession>> list = new GenericEntity<List<AAccession>>(acclist) {
                };
                responseBuilder.entity(list);
            }
            if (form.equals("AutoAssign")) {
                accessionCtype = accessionCtypeFacadeREST.find(collectionType);
                responseBuilder.type(MediaType.APPLICATION_XML).entity(accessionCtype);
            }
        }
        return responseBuilder.build();
    }

    //-------------------------------Merge and save - begin----------------------------------------------------------------//    
    @POST
    @Path("MergeAndSave")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String mergeAndSave(@FormParam("accIds") String accI_ds, @FormParam("accNos") String acc_Nos,
            @FormParam("autoAssigned") String autoAssigned, @FormParam("collectionType") String collectionType,
            @FormParam("prefixSuffix") String prefixSuffix, @FormParam("length") String length) {
        accIds = accI_ds.split(",");
        accNos = acc_Nos.split(",");
        List<String> differentTitle = new ArrayList<>();
        String firstTitle = "";


        List<Biblidetails> biblidetailsList = new ArrayList<>();
        for (int i = 0; i < accIds.length; i++) {
            locationList = locationFacadeREST.findBy("findByP852", accNos[i]);
            if (!locationList.isEmpty()) {
                alreadyExistingAccNo.add(locationList.get(0).getLocationPK().getP852());
                alreadyExistingArrayBuilder.add(locationList.get(0).getLocationPK().getP852());
                continue;
            }

            accession = find(Integer.parseInt(accIds[i]));
            accession.setAAAccno(accNos[i]);
            accession.setAADate(new Date());

            //Get Invoice if it is generated, but do not check for Gratis Item
            if (!"G".equals(accession.getARequest().getARStatus())) {
                invoiceList = aInvoiceFacadeREST.findBy("findByAIPoNoANDRegular", Integer.toString(accession.getARequest().getARPoNo().getAOPoNo()));
            }
            if (invoiceList.size() > 0) {
                invoice = invoiceList.get(0);
                accession.setAIInvoiceNo(invoice.getAIId());
                accession.setAIDiscount(BigDecimal.ZERO);
            }

            //entry in biblidetails only for first record
            //and use same record for all other titles
            if (i == 0) {
                biblidetails = createBiblidetails(accession);
                firstTitle = accession.getARequest().getARTitle();
            } else {
                if (!accession.getARequest().getARTitle().equals(firstTitle)) {
                    differentTitle.add(accIds[i]);
                    differentTitleArrayBuilder.add(accIds[i]);
                    continue;
                }
            }

            //set recordId
            accession.setACatRecid(Integer.toString(biblidetails.getBiblidetailsPK().getRecID()));

            //entry in location
            createLocation(accession, invoice);

            edit(accession);
            autoAssignedAccNo = accession.getAAAccno();
            accessionedArrayBuilder.add(accIds[i]);

        }

        //If Auto Assigned then update AccessionCType entry
        autoAssigned = autoAssigned;
        if ("true".equals(autoAssigned) && !"".equals(autoAssignedAccNo)) {
            UpdateAccessionCType(autoAssignedAccNo, collectionType, prefixSuffix, length);
        }

        if (alreadyExistingAccNo.size() > 0) {
            output = "Following Accession Nos. already exist please assign different Accession No. :" + "\n";
            String accNoMessage = ListToLangString(alreadyExistingAccNo);
            output = output + accNoMessage;
        }
        if (differentTitle.size() > 0) {
            output = output + "\n" + "Following Accession Ids. have different titles :" + "\n";
            String differentTitleAccIds = ListToLangString(differentTitle);
            output = output + differentTitleAccIds;
        }
        jsonObject = Json.createObjectBuilder()
                .add("alreadyExistingArray", alreadyExistingArrayBuilder.build())
                .add("differentTitleArray", differentTitleArrayBuilder.build())
                .add("accessionedIdsArray", accessionedArrayBuilder.build()).build();
        output = jsonObject.toString();
        return output;
    }

    static Biblidetails getBiblidetails(int recId, String tag, int tagSrNo, String sbFld, int sbFldSrNo, String ind, String fValue) {
        Biblidetails biblidetails = new Biblidetails();
        BiblidetailsPK biblidetailsPK = new BiblidetailsPK();

        biblidetailsPK.setRecID(recId);
        biblidetailsPK.setTag(tag);
        biblidetailsPK.setTagSrNo(tagSrNo);
        biblidetailsPK.setSbFld(sbFld);
        biblidetailsPK.setSbFldSrNo(sbFldSrNo);

        biblidetails.setInd(ind);
        biblidetails.setFValue(fValue);
        biblidetails.setBiblidetailsPK(biblidetailsPK);

        return biblidetails;

    }

    public void createLocation(AAccession accession, AInvoice invoice) {
        Location location = new Location();
        List<Location> locationList = new ArrayList<>();
        LocationPK locationPK = new LocationPK();
        int t852;
        ARequest req = accession.getARequest();
        //ObjectFactory objectFactory = new ObjectFactory();

        locationList = locationFacadeREST.findBy("findByMaxT852", accession.getACatRecid());

        if (locationList.isEmpty()) {
            t852 = 1;
        } else {
            t852 = locationList.get(0).getT852() + 1;
        }


        String issueRestricted = "N";
        locationPK.setP852(accession.getAAAccno());
        locationPK.setRecID(Integer.parseInt(accession.getACatRecid()));

        location.setLocationPK(locationPK);
        location.setT852(t852);
        location.setStatus("IP");
        // location.setIssueRestricted(Character.getNumericValue(issueRestricted));
        location.setIssueRestricted(issueRestricted);
        if (req.getARPrice() != null) {
            location.setPrice(req.getARPrice().toString());
        }
        location.setDateofAcq(new Date());
        if (req.getARSupCd() != null) {
            location.setSupplier(req.getARSupCd().getASName());
        }
        if (req.getARBudgetCd() != null) {
            location.setBudget(req.getARBudgetCd().getBudgetHead().getBudgetCode());
        }
        location.setMaterial(req.getARPhycd());
        location.setDepartment(req.getARDeptCd());
        location.setCurrency(req.getARCurrencyCd().getACCd());
        location.setF852("00000");
        location.setUserName("SuperUser");      //Need to be changed
        location.setLastOperatedDT(location.getDateofAcq());

        if (accession.getAIInvoiceNo() != null) {
            location.setInvoiceNo(accession.getAAAccno());
            location.setInvoiceDate(invoice.getAIDt());
        }

        //for Gratis Item: SupplierCode will Contain "Gratis Item"
        if ("G".equals(req.getARStatus())) {
            location.setSupplier("Gratis Item");
        }

        locationFacadeREST.create(location);

    }

    public void UpdateAccessionCType(String lastAccessNo, String collectionType, String prefixSuffix, String length) {
        AccessionCtype ctype = accessionCtypeFacadeREST.find(collectionType);
        ctype.setLastaccno(lastAccessNo);
        ctype.setPrefix(prefixSuffix);
        ctype.setTotallength(Integer.parseInt(length));
        accessionCtypeFacadeREST.edit(ctype);
    }

    public String ListToLangString(List<String> listToConvert) {
        String langString = "";
        int size = listToConvert.size();
        if (size == 1) {
            langString = listToConvert.get(0);
        } else {
            for (int i = 0; i < size - 1; i++) {
                langString = langString + "," + listToConvert.get(0);
            }
            langString = langString.substring(1);
            langString = langString + " and " + listToConvert.get(size - 1);
        }
        return langString;
    }
//------------------------- merge and save - end --------------------------------------//

    public Biblidetails createBiblidetails(AAccession accession) {
        Biblidetails bibdetails = new Biblidetails();
        List<Biblidetails> maxBibList = new ArrayList<>();
        int recId;
        ARequest req = accession.getARequest();
        //  ObjectFactory objectFactory = new ObjectFactory();

        maxBibList = bibdetailsFacadeREST.findBy("findByMaxRecID", "NULL");
        if (maxBibList.isEmpty()) {
            recId = 1;
        } else {
            recId = maxBibList.get(0).getBiblidetailsPK().getRecID() + 1;
        }

        //getBiblidetails(int recId, String tag, int tagSrNo, String sbFld, int sbFldSrNo, String ind, String fValue)

        bibdetails = getBiblidetails(recId, "000", 1, "", 0, null, "     nam a22     4a 4500");
        bibdetailsFacadeREST.create(bibdetails);



        bibdetails = getBiblidetails(recId, "008", 1, "", 0, null, "      n           #|||gr||||Z||||||     ");
        bibdetailsFacadeREST.create(bibdetails);


        if (req.getARTitle() != null) {
            bibdetails = getBiblidetails(recId, "245", 1, "a", 1, null, req.getARTitle());
            bibdetailsFacadeREST.create(bibdetails);

        }

        if (req.getARTitle() != null) {
            bibdetails = getBiblidetails(recId, "245", 1, "h", 1, null, req.getARPhycd().getDescription());
            bibdetailsFacadeREST.create(bibdetails);

        }

        if (req.getARFname() != null) {
            bibdetails = getBiblidetails(recId, "100", 1, "a", 1, null, req.getARFname());
            bibdetailsFacadeREST.create(bibdetails);
        }

        if (req.getARAuthor() != null) {
            bibdetails = getBiblidetails(recId, "100", 1, "e", 1, null, req.getARAuthor());
            bibdetailsFacadeREST.create(bibdetails);
        }

        if (req.getARPubCd() != null) {
            bibdetails = getBiblidetails(recId, "260", 1, "b", 1, null, req.getARPubCd().getASName());
            bibdetailsFacadeREST.create(bibdetails);
        }

        if (req.getARPubCd() != null) {
            bibdetails = getBiblidetails(recId, "260", 1, "a", 1, null, req.getARPubCd().getASCountry());
            bibdetailsFacadeREST.create(bibdetails);
        }

        if (req.getARIsbn() != null) {
            bibdetails = getBiblidetails(recId, "020", 1, "a", 1, null, req.getARIsbn());
            bibdetailsFacadeREST.create(bibdetails);
        }

        if (req.getAREdition() != null) {
            bibdetails = getBiblidetails(recId, "250", 1, "a", 1, null, req.getAREdition());
            bibdetailsFacadeREST.create(bibdetails);
        }

        if (req.getARRemark() != null) {
            bibdetails = getBiblidetails(recId, "500", 1, "a", 1, null, req.getARRemark());
            bibdetailsFacadeREST.create(bibdetails);
        }
        return bibdetails;
    }

    @GET
    @Path("findTitle/{Parameter}/{paraValue}")
    public List<BiblidetailsLocation> findTitle(@PathParam("Parameter") String parameter, @PathParam("paraValue") String paraValue) {
        List<BiblidetailsLocation> list = null;
        if ("Title".contentEquals(parameter)) {
            list = bibdetailsLocFacadeREST.findBy("findByTitleLike", paraValue);
        } else {
            list = bibdetailsLocFacadeREST.findBy("findByP852", paraValue);
        }

        return list;
    }

    @POST
    @Path("saveAvailable")
    @Consumes({"application/xml", "application/json", "application/x-www-form-urlencoded"})
    public String saveAvailable(Form form, @FormParam("accIds") String acc_Ids, @FormParam("accNos") String acc_Nos,
            @FormParam("autoAssigned") String autoAssigned, @FormParam("collectionType") String collectionType,
            @FormParam("prefixSuffix") String prefixSuffix, @FormParam("length") String length) {
        List<Biblidetails> biblidetailsList;
        accIds = acc_Ids.split(",");
        accNos = acc_Nos.split(",");

        for (int i = 0; i < accIds.length; i++) {
            locationList = locationFacadeREST.findBy("findByP852", accNos[i]);
            if (!locationList.isEmpty()) {
                alreadyExistingAccNo.add(locationList.get(0).getLocationPK().getP852());
                alreadyExistingArrayBuilder.add(locationList.get(0).getLocationPK().getP852());
                continue;
            }

            accession = find(Integer.parseInt(accIds[i]));
            accession.setAAAccno(accNos[i]);
            accession.setAADate(new Date());

            //Get Invoice if it is generated, but do not check for Gratis Item
            if (!"G".equals(accession.getARequest().getARStatus())) {
                invoiceList = aInvoiceFacadeREST.findBy("findByAIPoNoANDRegular", Integer.toString(accession.getARequest().getARPoNo().getAOPoNo()));
            }
            if (invoiceList.size() > 0) {
                invoice = invoiceList.get(0);
                accession.setAIInvoiceNo(invoice.getAIId());
                accession.setAIDiscount(BigDecimal.ZERO);
            }

            //entry in biblidetails
            if ("Reuse".equals(form.asMap().getFirst("saveOption" + i))) {
                biblidetailsList = bibdetailsFacadeREST.findBy("findByTitle", accession.getARequest().getARTitle());
                if (!biblidetailsList.isEmpty()) {
                    biblidetails = biblidetailsList.get(0);
                } else {
                    biblidetails = createBiblidetails(accession);
                }
            } else {
                biblidetails = createBiblidetails(accession);
            }

            //set recordId
            accession.setACatRecid(Integer.toString(biblidetails.getBiblidetailsPK().getRecID()));

            //entry in location
            createLocation(accession, invoice);

            edit(accession);
            autoAssignedAccNo = accession.getAAAccno();
            accessionedArrayBuilder.add(accIds[i]);

        }
        //If Auto Assigned then update AccessionCType entry
        if ("true".equals(autoAssigned) && !"".equals(autoAssignedAccNo)) {
            UpdateAccessionCType(autoAssignedAccNo, collectionType, prefixSuffix, length);
        }
        jsonObject = Json.createObjectBuilder()
                .add("alreadyExistingArray", alreadyExistingArrayBuilder.build())
                .add("differentTitleArray", differentTitleArrayBuilder.build())
                .add("accessionedIdsArray", accessionedArrayBuilder.build())
                .add("availableTitleArray", availableTitleBuilder.build()).build();


        output = jsonObject.toString();
        return output;
    }

    @POST
    @Path("MergeWithExisting")
    public String mergeWithExisting(@FormParam("accIds") String acc_Ids, @FormParam("accNos") String acc_Nos,
            @FormParam("title") String title, @FormParam("titleAccNo") String titleAccNo,
            @FormParam("autoAssigned") String autoAssigned, @FormParam("collectionType") String collectionType,
            @FormParam("prefixSuffix") String prefixSuffix, @FormParam("length") String length) {
        accIds = acc_Ids.split(",");
        accNos = acc_Nos.split(",");
        List<String> differentTitle = new ArrayList<>();
        String recordId = "";

        locationList = locationFacadeREST.findBy("findByP852", titleAccNo);
        if (!locationList.isEmpty()) {
            recordId = Integer.toString(locationList.get(0).getLocationPK().getRecID());
        }

        for (int i = 0; i < accIds.length; i++) {

            locationList = locationFacadeREST.findBy("findByP852", accNos[i]);
            if (!locationList.isEmpty()) {
                alreadyExistingAccNo.add(locationList.get(0).getLocationPK().getP852());
                alreadyExistingArrayBuilder.add(locationList.get(0).getLocationPK().getP852());
                continue;
            }

            accession = find(Integer.parseInt(accIds[i]));
            accession.setAAAccno(accNos[i]);
            accession.setAADate(new Date());

            //Get Invoice if it is generated, but do not check for Gratis Item
            if (!"G".equals(accession.getARequest().getARStatus())) {
                invoiceList = aInvoiceFacadeREST.findBy("findByAIPoNoANDRegular", Integer.toString(accession.getARequest().getARPoNo().getAOPoNo()));
            }
            if (invoiceList.size() > 0) {
                invoice = invoiceList.get(0);
                accession.setAIInvoiceNo(invoice.getAIId());
                accession.setAIDiscount(BigDecimal.ZERO);
            }

            //Not Similar Title check
            if (!accession.getARequest().getARTitle().equals(title)) {
                differentTitle.add(accIds[i]);
                differentTitleArrayBuilder.add(accIds[i]);
                continue;
            }

            //set recordId
            accession.setACatRecid(recordId);

            //entry in location
            createLocation(accession, invoice);

            edit(accession);
            autoAssignedAccNo = accession.getAAAccno();
            accessionedArrayBuilder.add(accIds[i]);

        }

        //If Auto Assigned then update AccessionCType entry
        if ("true".equals(autoAssigned) && !"".equals(autoAssignedAccNo)) {
            UpdateAccessionCType(autoAssignedAccNo, collectionType, prefixSuffix, length);
        }

        if (alreadyExistingAccNo.size() > 0) {
            output = "Following Accession Nos. already exist please assign different Accession No. :" + "\n";
            String accNoMessage = ListToLangString(alreadyExistingAccNo);
            output = output + accNoMessage;
        }
        if (differentTitle.size() > 0) {
            output = output + "\n" + "Following Accession Ids. have different titles :" + "\n";
            String differentTitleAccIds = ListToLangString(differentTitle);
            output = output + differentTitleAccIds;
        }
        jsonObject = Json.createObjectBuilder()
                .add("alreadyExistingArray", alreadyExistingArrayBuilder.build())
                .add("differentTitleArray", differentTitleArrayBuilder.build())
                .add("accessionedIdsArray", accessionedArrayBuilder.build()).build();
        output = jsonObject.toString();
        return output;
    }

    @POST
    @Path("Save")
    public String save(@FormParam("accIds") String acc_Ids, @FormParam("accNos") String acc_Nos,
            @FormParam("autoAssigned") String autoAssigned, @FormParam("collectionType") String collectionType,
            @FormParam("prefixSuffix") String prefixSuffix, @FormParam("length") String length) {
        accIds = acc_Ids.split(",");
        accNos = acc_Nos.split(",");
        List<Biblidetails> biblidetailsList;
        for (int i = 0; i < accIds.length; i++) {

            locationList = locationFacadeREST.findBy("findByP852", accNos[i]);
            if (!locationList.isEmpty()) {
                alreadyExistingAccNo.add(locationList.get(0).getLocationPK().getP852());
                alreadyExistingArrayBuilder.add(locationList.get(0).getLocationPK().getP852());
                continue;
            }

            accession = find(Integer.parseInt(accIds[i]));
            accession.setAAAccno(accNos[i]);
            accession.setAADate(new Date());

            //Informing user about avaible titles
            biblidetailsList = bibdetailsFacadeREST.findBy("findByTitle", accession.getARequest().getARTitle());

            if (!biblidetailsList.isEmpty()) {
                availableTitleBuilder.add(Json.createObjectBuilder()
                        .add("accId", accession.getAAccId())
                        .add("title", biblidetailsList.get(0).getFValue()).build());
                continue;
            }


            //Get Invoice if it is generated, but do not check for Gratis Item
            if (!"G".equals(accession.getARequest().getARStatus())) {
                invoiceList = aInvoiceFacadeREST.findBy("findByAIPoNoANDRegular", Integer.toString(accession.getARequest().getARPoNo().getAOPoNo()));
            }
            if (invoiceList.size() > 0) {
                invoice = invoiceList.get(0);
                accession.setAIInvoiceNo(invoice.getAIId());
                accession.setAIDiscount(BigDecimal.ZERO);
            }

            //entry in biblidetails
            biblidetails = createBiblidetails(accession);

            //set recordId
            accession.setACatRecid(Integer.toString(biblidetails.getBiblidetailsPK().getRecID()));

            //entry in location
            createLocation(accession, invoice);

            edit(accession);
            autoAssignedAccNo = accession.getAAAccno();
            accessionedArrayBuilder.add(accIds[i]);

        }
        //If Auto Assigned then update AccessionCType entry
        if ("true".equals(autoAssigned) && !"".equals(autoAssignedAccNo)) {
            UpdateAccessionCType(autoAssignedAccNo, collectionType, prefixSuffix, length);
        }

        jsonObject = Json.createObjectBuilder()
                .add("alreadyExistingArray", alreadyExistingArrayBuilder.build())
                .add("differentTitleArray", differentTitleArrayBuilder.build())
                .add("accessionedIdsArray", accessionedArrayBuilder.build())
                .add("availableTitleArray", availableTitleBuilder.build()).build();


        output = jsonObject.toString();
        return output;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("getAccessionForAccessioningProcess/by/{itemType}/{paramName}/{paramValue}")
    public List<AAccession> getAccessionForAccessioningProcessByParam(@PathParam("itemType") String itemType, @PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AAccession> getAccessionList = null;
        String status = null;
        if (itemType.equalsIgnoreCase("regular")) {
            status = "I";
        }
        if (itemType.equalsIgnoreCase("gratis")) {
            status = "G";
        }
        switch (paramName) {
            case "title":
                valueList.add(valueString[0]);
                valueList.add(status);
                getAccessionList = super.findBy("AAccession.findByAccnoNullANDTitle", valueList);
                break;
            case "supplier":
                valueList.add(valueString[0]);
                valueList.add(status);
                getAccessionList = super.findBy("AAccession.findByAccnoNullANDSupplier", valueList);
                break;
            case "receiveddate":
                try {
                    valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[0]));
                    valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                    valueList.add(status);
                } catch (ParseException ex) {
                    Logger.getLogger(AAccessionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                getAccessionList = super.findBy("AAccession.findByAccnoNullANDReceiveDate", valueList);
                break;
            case "orderNo":
                valueList.add(Integer.parseInt(valueString[0]));
                valueList.add(status);
                getAccessionList = super.findBy("AAccession.findByAccnoNullANDOrderNo", valueList);
                break;
        }
        return getAccessionList;
    }

    @GET
    @Path("getAccessionForAccessioningProcess/by/{itemType}")
    public List<AAccession> getAccessionForAccessioningProcess(@PathParam("itemType") String itemType) {
        List<AAccession> getAccessionList = null;
        if (itemType.equalsIgnoreCase("regular")) {
            getAccessionList = super.findBy("AAccession.findByAccnoNULLForRegular");
        }
        if (itemType.equalsIgnoreCase("gratis")) {
            getAccessionList = super.findBy("AAccession.findByAccnoNULLForGratis");
        }
        return getAccessionList;
    }

    @GET
    @Path("getLastAccessionNo/by/{collectionType}")
    public String getLastAccessionNo(@PathParam("collectionType") String collectionType) {
        String lastAccessionNo = null;
        AccessionCtype accessionCtype;
        accessionCtype = accessionCtypeFacadeREST.find(collectionType);
        lastAccessionNo = accessionCtype.getLastaccno();
        return lastAccessionNo;
    }

    //for report generation in accession
    @GET
    @Path("searchAccession/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<AAccession> getAccessionListByParam(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException {
        String[] valueString = paramValue.split(",");
        List<Object> valueList = new ArrayList<>();
        List<AAccession> getAccessionList = null;
        switch (paramName) {
            case "orderNo":
                valueList.add(Integer.parseInt(valueString[0]));
                getAccessionList = super.findBy("AAccession.findByOrderNo", valueList);
                break;
            case "budget":
                valueList.add(Integer.parseInt(valueString[0]));
                getAccessionList = super.findBy("AAccession.findByBudgetCd", valueList);
                break;
            case "accessionPrefix":
                valueList.add(valueString[0] + '%');
                getAccessionList = super.findBy("AAccession.findByAccessionPrefix", valueList);
                break;
            case "date":
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[0]));
                valueList.add(new SimpleDateFormat("yyyy-MM-dd").parse(valueString[1]));
                getAccessionList = super.findBy("AAccession.findByDateBetween", valueList);
                break;
            case "material":
                valueList.add(valueString[0]);
                getAccessionList = super.findBy("AAccession.findByMaterialCd", valueList);
                break;
            case "accessionRange":
                valueList.add(valueString[0]);
                valueList.add(valueString[1]);
                getAccessionList = super.findBy("AAccession.findByAccessionRange", valueList);
                break;
        }
        return getAccessionList;
    }

    // used in barcode generation in accession
    @GET
    @Path("getDataForBarcodeGeneration/{paramName}/{paramValue}")
    @Produces({"application/xml", "application/json"})
    public List<Object> getDataForBarcodeGeneration(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException {
        String q = "";
        Query query;
        String valuestring[] = paramValue.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (paramName) {
            case "orderNo":
                q = "select  concat('* ' ,a_accession.a_a_accno , ' *'),a_accession.a_a_accno from a_request,a_accession where a_request.a_r_no = a_accession.a_r_no \n"
                        + " and a_request.a_r_no in(Select distinct a_r_no from a_order where a_o_po_no in (\n"
                        + " Select a_o_po_no from a_ordermaster where a_o_po_no1 ='" + valuestring[0] + "'))\n"
                        + " and a_accession.a_a_accno is not null";
                break;
            case "budget":
                q = "select  concat('* ' ,a_accession.a_a_accno , ' *'),a_accession.a_a_accno from a_request,a_accession where a_request.a_r_no = a_accession.a_r_no \n"
                        + "  and a_request.a_r_no in(Select distinct a_r_no from a_request where a_r_budget_cd in (\n"
                        + "  Select a_b_budget_code from a_budget where budget_head = '" + valuestring[0] + "'))\n"
                        + "  and  a_accession.a_a_accno is not null";
                break;
            case "accessionPrefix":
                q = "Select concat('* ' ,a_accession.a_a_accno , ' *'),a_accession.a_a_accno from a_accession   where  a_accession.a_a_accno like '" + valuestring[0] + "%' \n"
                        + " and  a_accession.a_a_accno is not null\n"
                        + " order by a_accession.a_a_accno ";
                break;
            case "accessionNos":
                String getParamValues = "";
                for (int p = 0; p < valuestring.length; p++) {
                    getParamValues = getParamValues + "'" + valuestring[p] + "'";
                    if (p < (valuestring.length - 1)) {
                        getParamValues = getParamValues + ",";
                    }
                }
                q = "Select concat('* ' ,a_accession.a_a_accno , ' *'),a_accession.a_a_accno    from a_accession ,a_request  \n"
                        + " where a_accession.a_r_no = a_request.a_r_no and  \n"
                        + " a_request.a_r_no in(Select distinct a_r_no from a_accession where a_a_accno in (" + getParamValues + "))\n"
                        + " and a_accession.a_a_accno is not null ";
                break;
            case "date":
                Date date1 = new java.sql.Date(dateFormat.parse(valuestring[0]).getTime());
                Date date2 = new java.sql.Date(dateFormat.parse(valuestring[1]).getTime());
                q = " Select concat('* ' ,a_accession.a_a_accno , ' *'), a_accession.a_a_accno from a_accession ,a_request \n"
                        + " where a_accession.a_r_no = a_request.a_r_no and  \n"
                        + " a_request.a_r_date between '" + date1 + "' and '" + date2 + "' and a_accession.a_a_accno is not null";
                //remaining
                break;
        }
        System.out.println("query  " + q);
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();

        return result;
    }
//
//    //used in title in process
    @GET
    @Path("getTitleInProcess/{paramName}/{paramValue}")
     @Produces({"application/xml", "application/json"})
    public List<Object> getTitleInProcess(@PathParam("paramName") String paramName, @PathParam("paramValue") String paramValue) throws ParseException {
        String q = "";
        Query query;
        String valuestring[] = paramValue.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (paramName) {
            case "date":
                Date date1 = new java.sql.Date(dateFormat.parse(valuestring[0]).getTime());
                Date date2 = new java.sql.Date(dateFormat.parse(valuestring[1]).getTime());
                q = "select a_request.a_r_title as Title,a_accession.a_a_accno as Accno,a_accession.a_cat_recid as Recid,a_accession.a_a_date as "
                        + "Recdate from a_request,a_accession where a_request.a_r_no=a_accession.a_r_no and a_accession.a_a_accno "
                        + "is not NULL and a_accession.a_cat_recid is not NULL and (a_accession.a_a_incircul is null or "
                        + "a_accession.a_a_incircul='') and a_a_date between '" + date1 + "' and '" + date2 + "'";
                break;
            case "title":
                q = "select a_request.a_r_title as Title,a_accession.a_a_accno as Accno,a_accession.a_cat_recid as Recid,a_accession.a_a_date as "
                        + "Recdate from a_request,a_accession where a_request.a_r_no=a_accession.a_r_no and a_accession.a_a_accno "
                        + "is not NULL and a_accession.a_cat_recid is not NULL and (a_accession.a_a_incircul is null or "
                        + "a_accession.a_a_incircul='') and a_request.a_r_title Like '" + valuestring[0] + "%'";
                break;
            case "accession":
                q = "select a_request.a_r_title as Title,a_accession.a_a_accno as Accno,a_accession.a_cat_recid as Recid,a_accession.a_a_date as "
                        + "Recdate from a_request,a_accession where a_request.a_r_no=a_accession.a_r_no and a_accession.a_a_accno "
                        + "is not NULL and a_accession.a_cat_recid is not NULL and (a_accession.a_a_incircul is null or "
                        + "a_accession.a_a_incircul='') and a_accession.a_a_accno = '" + valuestring[0] + "'";
                break;
            default:
                q = "select a_request.a_r_title as Title,a_accession.a_a_accno as Accno,a_accession.a_cat_recid as Recid,a_accession.a_a_date as "
                        + "Recdate from a_request,a_accession where a_request.a_r_no=a_accession.a_r_no and a_accession.a_a_accno "
                        + "is not NULL and a_accession.a_cat_recid is not NULL and (a_accession.a_a_incircul is null or "
                        + "a_accession.a_a_incircul='')";
                break;
        }
        System.out.println("query  " + q);
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();

        return result;
    }
}
