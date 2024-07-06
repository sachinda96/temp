/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.Pattern;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import soul.catalogue.BiblidetailsLocation;
import soul.catalogue.Location;
import soul.catalogue.service.BiblidetailsLocationFacadeREST;
import soul.catalogue.service.LocationFacadeREST;
import soul.general_master.IllLibmst;
import soul.general_master.service.IllLibmstFacadeREST;
import soul.circulation.TBookTransfer;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.tbooktransfer")
public class TBookTransferFacadeREST extends AbstractFacade<TBookTransfer> {

    @EJB
    private LocationFacadeREST locationFacadeREST;
    @EJB
    private IllLibmstFacadeREST illLibmstFacadeREST;
    @EJB
    private BiblidetailsLocationFacadeREST biblidetailsLocationFacadeREST;

    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;
    String output = "";
    String accNo;
    String[] acceNos, transferIds;
    List<Location> locationList = new ArrayList<>();
    List<IllLibmst> librarylist = new ArrayList<>();
    List<TBookTransfer> bookTransferList = new ArrayList<>();
    Location location;
    TBookTransfer bookTransfer = null;
    Date todaydate = new Date();    
    public TBookTransferFacadeREST() {
        super(TBookTransfer.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TBookTransfer entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(TBookTransfer entity) {
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
    public TBookTransfer find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TBookTransfer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TBookTransfer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    //Added manually
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<TBookTransfer> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values) {
        String[] valueString = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch (query) {
            case "findByAccNo":
                valueList.add(valueString[0]);
                return super.findBy("TBookTransfer." + query, valueList);
            default:
                valueList.add(valueString[0]);
                //used for findByGroupName, findWithMaxSrNo, RemoveByMemCd, 
                break;
        }
        return super.findBy("TBookTransfer." + query, valueList);
    }

    @GET
    @Path("byQuery/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Object> findByQuery(@PathParam("namedQuery") String namedQuery, @PathParam("values") String values) {
        String q = "";
        Query query;
        switch (namedQuery) {
            case "forBookTransfer":
                q = "SELECT DISTINCT Location.p852, Location.Status, \n"
                        + "LibMaterials.Description, Biblidetails.FValue, Biblidetails_1.FValue AS Expr1\n"
                        + "FROM  Location INNER JOIN \n"
                        + "Biblidetails ON Location.RecID = Biblidetails.RecID INNER JOIN \n"
                        + "Biblidetails AS Biblidetails_1 ON Location.RecID = Biblidetails_1.RecID INNER JOIN \n"
                        + "LibMaterials ON Location.Material = LibMaterials.Code where Biblidetails.Tag='245' \n"
                        + "and Biblidetails.sbFld='a' and Biblidetails_1.Tag='100' \n"
                        + "and Biblidetails_1.sbFld='a' and Location.p852 = '" + values + "'";
                break;
            case "forBookReceive":
                q = "SELECT transfer_id, ill_libmst.lib_nm AS source_lib, destLib.lib_nm AS destintion_lib, acc_no, user_cd, transfer_Dt, STATUS , FValue, author\n"
                        + "FROM  `t_book_transfer` AS bookTransfer\n"
                        + "LEFT JOIN ill_libmst ON bookTransfer.source_lib = ill_libmst.lib_cd\n"
                        + "INNER JOIN ill_libmst AS destLib ON bookTransfer.dest_lib = destLib.lib_cd\n"
                        + "INNER JOIN (\n"
                        + "\n"
                        + "SELECT p852, biblidetails.FValue, bib.FValue AS author\n"
                        + "FROM  `location` \n"
                        + "INNER JOIN  `biblidetails` ON location.RecID = biblidetails.RecID\n"
                        + "INNER JOIN  `biblidetails` AS  `bib` ON location.RecID = bib.RecID\n"
                        + "WHERE biblidetails.Tag =  '245'\n"
                        + "AND biblidetails.SbFld =  'a'\n"
                        + "AND bib.Tag =  '100'\n"
                        + "AND bib.SbFld =  'a'\n"
                        + ") AS bookDetails ON bookDetails.p852 = bookTransfer.acc_no";
                break;
        }
        List<Object> result;
        query = getEntityManager().createNativeQuery(q);
        result = (List<Object>) query.getResultList();

        return result;
    }

    @GET
    @Path("readForBookTransfer/{accNo}/{sourceLibrary}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json", "text/plain"})
    //public Response readForBookTransfer(@QueryParam("accNo") String accNo, @QueryParam("sourceLibrary") String sourceLibrary) {
    public Response readForBookTransfer(@PathParam("accNo") String accNo, @PathParam("sourceLibrary") String sourceLibrary) {    
        Response.ResponseBuilder responseBuilder = Response.status(200);
        locationList = locationFacadeREST.findBy("findByP852", accNo);
        if (locationList.isEmpty()) // Checks if accession no is valid or not
        {
            output = "Book Code does not exist, please enter valid Code.";
            responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
        } else {

            bookTransferList = findBy("findByAccNo", accNo);
            Boolean transfered = false;
            if (!bookTransferList.isEmpty()) //To check if book is already transfered
            {
                for (TBookTransfer tb : bookTransferList) {
                    if (tb.getStatus().equals("T")) {
                        transfered = true;
                        bookTransfer = tb;
                    }
                }
            }

            if (transfered) //If book is already transfered alert user and does not allow them to transfer it again
            {
                output = "Book is already transfered from " + bookTransfer.getSourceLib() + " to " + bookTransfer.getDestLib() + ".";
                responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
            } else //Book is not already transfered
            {
                location = locationList.get(0);

                if (!sourceLibrary.equals(location.getF852())) //if Book does not belong to source lib as mentioned by user alert them
                {
                    output = "Book does not belong to this library.";
                    responseBuilder.type(MediaType.TEXT_PLAIN).entity(output);
                } else {
                    List<BiblidetailsLocation> bibDetailslist = biblidetailsLocationFacadeREST.findBy("findByP852", accNo);
                    GenericEntity<List<BiblidetailsLocation>> list = new GenericEntity<List<BiblidetailsLocation>>(bibDetailslist) {
                    };
                    responseBuilder.entity(list);
                }
            }
        }
        return responseBuilder.build();
    }

    @POST
    @Path("bookTransfer/{accessionNos}/{sourceLibrary}/{destinationLibrary}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public String bookTransfer( @PathParam("accessionNos") String accession_Nos,
            @PathParam("sourceLibrary") String sourceLibrary,
            @PathParam("destinationLibrary") String destinationLibrary) {
        String output = null;
        String library = null;
        acceNos = accession_Nos.split(",");
        String transfered = "";
        String invalid = "";
        String notTransfered = "";
        String notAvailable = "";
        librarylist = (List<IllLibmst>) illLibmstFacadeREST.findBy("findByLibCd", sourceLibrary);
        try {
            library = librarylist.get(0).getLibNm();
        } catch (Exception e) {
            library = "Main Library";
        }
        if (sourceLibrary.equals(destinationLibrary)) {
            return "Source library and Destination library should be different.";
        } else {
            bookTransfer = new TBookTransfer();
            for (int i = 0; i < acceNos.length; i++) {
                boolean found = false;
                //locationList = (List<Location>) locationFacadeREST.findBy("findByP852", acceNos[i]);
                try {
                    locationList = (List<Location>) locationFacadeREST.getByAcc(acceNos[i]);
                    found = acceNos[i].equals(locationList.get(0).getLocationPK().getP852());
                } catch (ArrayIndexOutOfBoundsException e) {
                    output = "Invalid accession no: " + acceNos[i];
                }
//                System.out.println("size: " + locationList.get(0).getLocationPK().getP852());
//                System.out.println("size: " + locationList.size());
//                System.out.println("size: " + acceNos[i]);

                if (found) {
                    if (sourceLibrary.equals(locationList.get(0).getF852())) {
                        if (locationList.get(0).getStatus().contentEquals("AV")) {
//                            System.out.println(locationList.get(0).getF852());
                            //bookTransfer = new TBookTransfer();
                            bookTransfer.setSourceLib(sourceLibrary);
//                            System.out.println(destinationLibrary);
                            bookTransfer.setDestLib(destinationLibrary);
                            bookTransfer.setStatus("T");
                            bookTransfer.setAccNo(acceNos[i].trim());

                            
                                //   bookTransfer.setTransferDt(new SimpleDateFormat("yyyy-MM-dd").parse(transfdt));
                                bookTransfer.setTransferDt(todaydate);
                           
                            bookTransfer.setUserCd("super user");

                            System.out.println(librarylist);
                            /*if (sourceLibrary == librarylist.get(0).getLibCd()) {
                            System.out.println("");
                            }*/
                            create(bookTransfer);
                            //transfered = transfered + " " + acceNos[i] + ",";
                            transfered = String.join(",", acceNos[i]);
                            //output = "Book Transfereed:" +acceNos[i];
                            if (!locationList.isEmpty()) {
                                location = locationList.get(0);
                                location.setF852(bookTransfer.getDestLib());
                                locationFacadeREST.edit(location);
                            }
                        } else {
                            notAvailable = String.join(",", acceNos[i]);
                            return "Book with Accession nos \'" + notAvailable + "\' are not available for transfer. Status: \'"+locationList.get(0).getStatus()+"\'. Please remove such books and try again.";
                        }
                    } else {
                        notTransfered = String.join(",", acceNos[i]);
                        //notTransfered = notTransfered + "" + acceNos[i] + ",";
                        return "Book with Accession nos \'" + notTransfered + "\' doesn't belong to \'" + library + "\'. Please remove such books and try again.";
                    }
                } else {
                    //invalid = invalid + " " + acceNos[i] + ",";
                    invalid = String.join(",", acceNos[i]);
                    return "Invalid accession nos: " + invalid;
                    //output = "Book not trnasferred no: "+acceNos[i];
                }
            }
        }
        output = "Book with accession_no transfered: " + transfered + " ";
        return output;
    }

    @PUT
    @Path("receiveBook/{transferIds}")
    @Produces({"application/xml", "application/json",MediaType.TEXT_PLAIN})
    public String receiveBook(@PathParam("transferIds") String transfer_Ids) {
        System.out.println("transfer_Ids---"+transfer_Ids);
        transferIds = transfer_Ids.split(",");
        System.out.println("transferIds[0]---"+transferIds[0]);
        for (int i = 0; i < transferIds.length; i++) {
            try {
                bookTransfer = find(Integer.parseInt(transferIds[i]));
                bookTransfer.setStatus("R");
            } catch (NullPointerException e) {
                return "Transfer id invalid";
            }

            bookTransfer.setReturnDt(todaydate);
            
            edit(bookTransfer);
            locationList = (List<Location>) locationFacadeREST.findBy("findByP852", bookTransfer.getAccNo());
            if (!locationList.isEmpty()) {
                location = locationList.get(0);
                location.setF852(bookTransfer.getSourceLib());
                locationFacadeREST.edit(location);
            }
        }
        output = "Book(s) received In respective parent library.";
        return output;
    }
}
