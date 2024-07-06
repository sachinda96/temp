/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.general_master.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import soul.general_master.Reportimage;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.general_master.reportimage")
public class ReportimageFacadeREST extends AbstractFacade<Reportimage> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public ReportimageFacadeREST() {
        super(Reportimage.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Reportimage entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Reportimage entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Reportimage find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Reportimage> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Reportimage> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("retrieveAll")
    @Produces({"application/xml", "application/json"})
    public List<Reportimage> retrieveAll() {
        return super.findAll();
    }
    
    @PUT
    @Path("uploadImage")
    @Consumes({"multipart/form-data","application/xml"})  
          public Response updateFile( @FormDataParam("file") FormDataContentDisposition fileDetail,@FormDataParam("file") InputStream uploadedInputStream ,@FormDataParam("filename") String filename ,
          @FormDataParam("Id") String Id ) {
        try {
            System.out.println("11111111  "+fileDetail.getFileName());
             System.out.println("22222222  "+fileDetail.getName());
                saveUpdatedFileToDb(uploadedInputStream, filename, Id);
            }
        catch (IOException e) 
            {
                return Response.status(500).entity("Can not upload Image").build();
            }
            return Response.status(200).entity("Image Uplaoaded.").build();
     }
	
    private void saveUpdatedFileToDb(InputStream inStream,  String fileName , String Id)
			throws IOException {
           
            Reportimage reportimage = find(Long.parseLong(Id));
            
            int read = 0;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();			//System.out.println(Streams.asString(content));
        
            int nRead;
            byte[] data = new byte[16384];
            if(inStream != null)
            {
                   while ((nRead = inStream.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                   } 
         //   System.out.println(""+buffer.toByteArray()+"   "+fileName+"  "+getFileExtension(fileName));
            //reportimage.getDescription("."+getFileExtension(fileName));
           // biblifile.setFileData(buffer.toByteArray());
           // biblifile.setFileName(fileName);
           // biblifile.setRecID(Integer.parseInt(recId));
            reportimage.setImageData(buffer.toByteArray());
            edit(reportimage);
            }
	}

    
//    @GET
//    @Path("getEditFileName/{RecId}")
//    @Produces("text/plain")
//    public String getEditFileName(@PathParam("RecId") String recId)
//    {
//          String output = null;
//          List<Biblifiles> filelist =  findBy("findByRecID", recId);
//          if(filelist.size() > 0)
//            {
//              output = filelist.get(0).getFileName();
//            }
//          return output;
//    }
    
//     private String getFileNames(MultivaluedMap<String, String> header) {
//
//		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
//
//		for (String filename : contentDisposition) {
//			if ((filename.trim().startsWith("filename"))) {
//
//				String[] name = filename.split("=");
//
//				String finalFileName = name[1].trim().replaceAll("\"", "");
//				return finalFileName;
//			}
//		}
//		return "unknown";
//	}
    
//   private String getFileExtension(String content) {
//            return content.substring(
//                    content.indexOf('.') + 1).trim().replace("\"", "");
//}
//    
    
}
