package api;

import java.util.logging.Logger;

@Path("profil")
public class Resource {

    private static final Logger LOGGER = Logger.getLogger(Resource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response root(@Context UriInfo uriInfo) throws FileItemNotFoundException, FileServiceException {
        LOGGER.log(Level.INFO, "GET /api/files");
        FileItem item = filestore.get("");
        URI root = uriInfo.getRequestUriBuilder().path(item.getId()).build();
        return Response.seeOther(root).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response rootView(@Context UriInfo uriInfo) throws FileItemNotFoundException, FileServiceException {
        LOGGER.log(Level.INFO, "GET /api/files (html)");
        FileItem item = filestore.get("");
        URI root = uriInfo.getRequestUriBuilder().path(item.getId()).path("content").build();
        return Response.seeOther(root).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public FileItem get(@PathParam("id") String id) throws FileItemNotFoundException, FileServiceException {
        LOGGER.log(Level.INFO, "GET /api/files/" + id);
        FileItem item = filestore.get(id);
        return item;
    }

    @GET
    @Path("{id}/content")
    @Template(name = "files")
    @Produces(MediaType.APPLICATION_JSON)
    public Response content(@PathParam("id") String id) throws FileItemNotFoundException, FileServiceException {
        LOGGER.log(Level.INFO, "GET /api/files/" + id + "/content");
        FileItem item = filestore.get(id);
        if ( item.isFolder() ) {
            return Response.ok(filestore.list(item.getId())).build();
        } else {
            return Response.ok(filestore.getContent(item.getId()))
                    .header("Content-Type", item.getMimeType())
                    .header("Content-Length", item.getSize())
                    .header("Content-Disposition", "attachment; filename=" + item.getName()).build();
        }
    }

    @GET
    @Path("{id}/content")
    @Template(name = "files")
    @Produces(MediaType.TEXT_HTML)
    public Response contentView(@PathParam("id") String id, @QueryParam("download") @DefaultValue("false") boolean download) throws FileItemNotFoundException, FileServiceException {
        LOGGER.log(Level.INFO, "GET /api/files/" + id + "/content (html)");
        FileItem item = filestore.get(id);
        if ( item.isFolder() ) {
            TemplateContent<Map<String, Object>> content = new TemplateContent<>();
            Map<String, Object> value = new HashMap<>();
            value.put("profile", auth.getConnectedProfile());
            value.put("parent", item);
            value.put("path", filestore.path(item.getId()));
            value.put("items", filestore.list(item.getId()));
            content.setContent(value);
            return Response.ok(content).build();
        } else {
            return Response.ok(filestore.getContent(item.getId()))
                    .header("Content-Type", item.getMimeType())
                    .header("Content-Length", item.getSize())
                    .header("Content-Disposition", ((download) ? "attachment; " : "") + "filename=" + item.getName()).build();
        }
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response add(@PathParam("id") String id, @MultipartForm @Valid FileUploadForm form, @Context UriInfo info) throws FileItemAlreadyExistsException, FileServiceException, FileItemNotFoundException, IOException {
        LOGGER.log(Level.INFO, "POST /api/files/" + id);
        FileItem item;
        if ( form.getData() != null ) {
            item = filestore.add(id, form.getName(), form.getData());
        } else {
            item = filestore.add(id, form.getName());
        }
        URI createdUri = info.getBaseUriBuilder().path(Resource.class).path(item.getId()).build();
        return Response.created(createdUri).build();
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addView(@PathParam("id") String id, @MultipartForm @Valid FileUploadForm form, @Context UriInfo info) throws FileItemAlreadyExistsException, FileServiceException, FileItemNotFoundException, IOException {
        LOGGER.log(Level.INFO, "POST /api/files/" + id + " (html)");
        if ( form.getData() != null ) {
            filestore.add(id, form.getName(), form.getData());
        } else {
            filestore.add(id, form.getName());
        }
        URI createdUri = info.getBaseUriBuilder().path(Resource.class).path(id).path("content").build();
        return Response.seeOther(createdUri).build();
    }

    @PUT
    @Path("{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(@PathParam("id") String id, @PathParam("name") String name, @MultipartForm FileUploadForm form) throws FileItemNotFoundException, FileServiceException, FileItemNotEmptyException, FileItemAlreadyExistsException {
        LOGGER.log(Level.INFO, "PUT /api/files/" + id + "/" + name);
        filestore.remove(id, name);
        filestore.add(id, name, form.getData());
        return Response.noContent().build();
    }
    
    @DELETE
    @Path("{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response delete(@PathParam("id") String id, @PathParam("name") String name) throws FileItemNotFoundException, FileServiceException, FileItemNotEmptyException {
        LOGGER.log(Level.INFO, "DELETE /api/files/" + name);
        filestore.remove(id, name);
        return Response.noContent().build();
    }

}
