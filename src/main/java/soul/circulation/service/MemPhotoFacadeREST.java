/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.circulation.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.apache.commons.fileupload.FileItem;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import soul.circulation.MemPhoto;

/**
 *
 * @author soullab
 */
@Stateless
@Path("soul.circulation.memphoto")
public class MemPhotoFacadeREST extends AbstractFacade<MemPhoto> {
    @PersistenceContext(unitName = "SoulRestAppPU")
    private EntityManager em;

    public MemPhotoFacadeREST() {
        super(MemPhoto.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MemPhoto entity) {
        System.out.println(entity);
        if(entity == null){
            System.out.print("i am in the entity");
        }
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MemPhoto entity) {
        System.out.println("i am in edit");
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MemPhoto find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MemPhoto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MemPhoto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    
     @POST
    @Path("uploadMemPhoto")
    @Consumes({"multipart/form-data","application/xml"})  
          public String uploadMemPhoto( @FormDataParam("addPhoto") InputStream uploadedInputStream,
                 @FormDataParam("memCode") String memCode, @FormDataParam("name") String name ) { 
           String output= "";   
        try {
            saveUpdatedFileToDb(uploadedInputStream, memCode, name);
            output = "Data saved successfully";
        } catch (IOException ex) {
            Logger.getLogger(MemPhotoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
             output = "Error in saving photo";
        }
              return output;
          }
    
    
    @POST
    @Path("uploadFolder")
    @Consumes({"multipart/form-data","application/xml"})  
          public Response uploadFolder( @FormDataParam("imageFile") List<FileItem> uploadedInputStream  ) {
        try {
           
             List<FileItem> items = uploadedInputStream;
             
//            for(int i=0;i<fitem.size();i++)
//            {
//                 System.out.println("11111111  "+fileDetail.get(i).getFileName());
//             System.out.println("22222222  "+fileDetail.get(i).getName());
//              //  FileInputStream fi = new FileInputStream(f.get(i));
//                fitem.w
//             //   saveUpdatedFileToDb(fi);
//            }
             String name = null;
             if (items != null) {
                   MemPhoto photo = new MemPhoto();
                 Iterator<FileItem> iter = items.iterator();
                    /*
                     * Return true if the instance represents a simple form
                     * field. Return false if it represents an uploaded file.
                     */
                    while (iter.hasNext()) {
                        final FileItem item = iter.next();
                        final String itemName = item.getName();
                        final String fieldName = item.getFieldName();
                        final String fieldValue = item.getString();
                        if (item.isFormField()) {
                            name = fieldValue;
                            System.out.println("Field Name: " + fieldName + ", Field Value: " + fieldValue);
                            System.out.println("Candidate Name: " + name);
                        } else {
//                            final File file = new File(FILE_UPLOAD_PATH    + File.separator + itemName);
//                            File dir = file.getParentFile();
//                            if(!dir.exists()) {
//                                dir.mkdir();
//                            }
//                            
//                            if(!file.exists()) {
//                                file.createNewFile();
//                            }
                         //   System.out.println("Saving the file: " + file.getName());
                            System.out.println("getFileNames   "+item.getHeaders().getHeader("filename"));
                            System.out.println("item.get()  .. "+item.get());
                            photo.setMemberPhoto(item.get());
                        }
            }
             }
             else{
                 System.out.println("item is nul..........");
             }
        }
        catch (Exception e) 
            {
                return Response.status(500).entity("Can not upload Image").build();
            }
            return Response.status(200).entity("Image Uplaoaded.").build();
     }

    
    public  void saveUpdatedFileToDb(InputStream inStream , String id , String name)
			throws IOException {
           
            System.out.println(id);
            MemPhoto photo = new MemPhoto();
            int read = 0;
            ByteArrayOutputStream buffer = null;		//System.out.println(Streams.asString(content));
        
            int nRead;
            byte[] data = new byte[16384];
            if(inStream != null)
            {
                 buffer = new ByteArrayOutputStream();	
                   while ((nRead = inStream.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                   } 
               System.out.println("fnnnnnnnnnammmmmm  "+name);
               photo.setMemCd(id);
               photo.setMemPhotoPath(name);
               photo.setMemberPhoto(buffer.toByteArray());
            }
            else{
               photo.setMemCd(id);
               photo.setMemPhotoPath(null);
               photo.setMemberPhoto(null);
            }
            System.out.print(id);
               create(photo);
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
}
