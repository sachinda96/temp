/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

import soul.catalogue.Biblidetails;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
//import org.apache.wink.common.model.multipart.BufferedInMultiPart;
//import org.apache.wink.common.model.multipart.BufferedOutMultiPart;

//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import com.sun.jersey.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataParam;
//import com.sun.jersey.core.header.FormDataContentDisposition;
//import java.io.FileOutputStream;
//import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.FileOutputStream;
//import org.glassfish.jersey.media.multipart.FormDataParam;
//import com.sun.jersey.multipart.

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import soul.catalogue.Biblifiles;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.catalogue.biblifiles")
public class BiblifilesFacadeREST extends AbstractFacade<Biblifiles> {
    @EJB
    private BiblidetailsFacadeREST biblidetailsFacadeREST;
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public BiblifilesFacadeREST() {
        super(Biblifiles.class);
    }

    
//    @POST
//@Path("upload/{id}")
//@Consumes({"application/x-www-form-urlencoded", "multipart/form-data"})
//
//public void addBlob(@PathParam("id") Integer id, @FormDataParam("file") InputStream uploadedInputStream) throws IOException
//    {
//   // E24ClientTemp entityToMerge = find(id);
//    try {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int read = 0;
//        byte[] bytes = new byte[1024];
//        while ((read = uploadedInputStream.read(bytes)) != -1) {
//            out.write(bytes, 0, read);
//        }
//       // entityToMerge.setTestBlob(out.toByteArray());
//        super.edit(entityToMerge);
//    }
//    catch (IOException e) {
//        e.printStackTrace();
//    }
//}
    
//    @POST
//  //  @Override
//    @Path("saveFile")
//  @Consumes({"application/x-www-form-urlencoded", "multipart/form-data"})
//   public String create(@FormDataParam("uploadedFile") InputStream uploadedInputStream) throws IOException {
//       String fileName = "";
//      
//		//Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
//                
//		//List<InputPart> inputParts = uploadForm.get("uploadedFile");
//                System.out.println("operation "+"operation");
//               // System.out.println("operation ttttt"+operationt);
//                List<Biblidetails> countRecId = biblidetailsFacadeREST.findBy( "findByMaxRecID", "NULL");
//                  int nextRecId = (countRecId.get(0).getBiblidetailsPK().getRecID()) ;
//
//		//for (InputPart inputPart : inputParts) {
//                   // System.out.println("calling");
//		 try {
//                        Biblifiles biblifile = new Biblifiles();
//			//MultivaluedMap<String, String> header = inputPart.getHeaders();
//			//fileName = getFileName(header);
//fileName = "aaa.txt";
//			//convert the uploaded file to inputstream
//			//InputStream inputStream = inputPart.getBody(InputStream.class,null);
//
//			byte [] bytes = IOUtils.toByteArray(uploadedInputStream);
//                        biblifile.setExtension("."+getFileExtension(fileName));
//                        biblifile.setFileData(bytes);
//                        biblifile.setFileName(fileName);
//                        biblifile.setRecID(nextRecId);
//                        super.create(biblifile);
//			//constructs upload file path
//			//fileName = UPLOADED_FILE_PATH + fileName;
//
//			//writeFile(bytes,fileName);
//
//			System.out.println("Done");
//
//		  } catch (IOException e) {
//			e.printStackTrace();
//		  }
//
//		//}
//                
//         return "success";
//    }

//   @POST
//   @Path("saveFile")
//   @Consumes("multipart/form-data")
//    public void postFormData( BufferedInMultiPart bimp) {
//     //  BufferedInMultiPart bimp = 
//       List<InPart> parts = bimp.getParts();
//       parts.get(0).getHeaders();
//      String  fileName = getFileName(parts.get(1).getHeaders());
//       System.out.println("filename........  "+fileName);
//    // echo what we got in the form
////    BufferedOutMultiPart bomp = new BufferedOutMultiPart();
//// 
////    OutPart op = new OutPart();
////    op.setBody(theFile);
////    op.setContentType(MediaType.TEXT_PLAIN);  // or other appropriate type, based on the file you received
////    
////    bomp.addPart(op);
////    op = new OutPart();
//// //   op.setBody(theDescription);
////    op.setContentType(MediaType.TEXT_PLAIN);
////    bomp.setLocationHeader("description");
////    bomp.addPart(op);
////    InputStream inputStream = op.
////    BufferedOutMultiPart bomp = new BufferedOutMultiPart();
////    OutPart op = new OutPart();
////    op.setBody(theFileid + "");  // just in case theFileid is uninitialized
////    op.setContentType(MediaType.TEXT_PLAIN);
////    bomp.setLocationHeader("fileid");
////    bomp.addPart(op);
//   
//  
//}
   
    @POST
    @Path("saveFile/{filename}")
    @Consumes({"multipart/form-data","application/xml"})
    @Produces("text/plain")
    public String uploadFile(@FormParam("recId") int recId,@FormDataParam("file") InputStream uploadedInputStream ,@PathParam("filename") String filename ) {
        try {
            
                saveFileToDb(recId,uploadedInputStream, filename);
            }
        catch (IOException e) 
            {
                 Response.status(500).entity("Can not save file").build();
                 return "Can not save file";
            }
                 Response.status(200).entity("success").build();
                  return "success";
     }
	
	private void saveFileToDb(int recId,InputStream inStream,  String fileName)
			throws IOException {
            
            Biblifiles biblifile = new Biblifiles();
            OutputStream  o = new ByteArrayOutputStream();
            int read = 0;
//                 System.out.println("filePart :"+filePart.getContentType());
            //byte[] bytes = new byte[1024];
//                filecontent = filePart.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();			//System.out.println(Streams.asString(content));
            int nRead;
            byte[] data = new byte[16384];
            
                   while ((nRead = inStream.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                   } 
            System.out.println(""+buffer.toByteArray()+"   "+fileName+"  "+getFileExtension(fileName));
            biblifile.setExtension("."+getFileExtension(fileName));
            biblifile.setFileData(buffer.toByteArray());
            biblifile.setFileName(fileName);
           // List<Biblidetails> countRecId = biblidetailsFacadeREST.findBy( "findByMaxRecID", "NULL");
          //  int nextRecId = (countRecId.get(0).getBiblidetailsPK().getRecID()) ;
            biblifile.setRecID(recId);
            super.create(biblifile);
	}
	
    @PUT
    @Path("updateFile/{filename}/{recId}")
    @Consumes({"multipart/form-data","application/xml"})  
          public Response updateFile(@FormDataParam("file") InputStream uploadedInputStream ,@PathParam("filename") String filename ,
          @PathParam("recId") String recId ) {
        try {
                saveUpdatedFileToDb(uploadedInputStream, filename, recId);
            }
        catch (IOException e) 
            {
                return Response.status(500).entity("Can not update file").build();
            }
            return Response.status(200).entity("File updated.").build();
     }
	
    private void saveUpdatedFileToDb(InputStream inStream,  String fileName , String recId)
			throws IOException {
            
            Biblifiles biblifile = new Biblifiles();
            int read = 0;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();			//System.out.println(Streams.asString(content));
        
            int nRead;
            byte[] data = new byte[16384];
            if(inStream != null)
            {
                   while ((nRead = inStream.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                   } 
            System.out.println(""+buffer.toByteArray()+"   "+fileName+"  "+getFileExtension(fileName));
            biblifile.setExtension("."+getFileExtension(fileName));
            biblifile.setFileData(buffer.toByteArray());
            biblifile.setFileName(fileName);
            biblifile.setRecID(Integer.parseInt(recId));
            super.edit(biblifile);
            }
	}

    
    @GET
    @Path("getEditFileName/{RecId}")
    @Produces("text/plain")
    public String getEditFileName(@PathParam("RecId") String recId)
    {
          String output = null;
          List<Biblifiles> filelist =  findBy("findByRecID", recId);
          if(filelist.size() > 0)
            {
              output = filelist.get(0).getFileName();
            }
          return output;
    }
    
     private String getFileNames(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
    
   private String getFileExtension(String content) {
            return content.substring(
                    content.indexOf('.') + 1).trim().replace("\"", "");
}
    
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Biblifiles entity) {
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
    public Biblifiles find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Biblifiles> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Biblifiles> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("by/{namedQuery}/{values}")
    @Produces({"application/xml", "application/json"})
    public List<Biblifiles> findBy(@PathParam("namedQuery") String query, @PathParam("values") String values){
        String[] valueSting = values.split(",");
        List<Object> valueList = new ArrayList<>();
        switch(query)
        {
          
            case "findByRecID" :valueList.add(Integer.parseInt(valueSting[0]));
                break;
            default:    valueList.add(valueSting[0]);
                        break;
        }
        return super.findBy("Biblifiles."+query, valueList);
    }
    
    
}
