# Rest Template Get()

<p align="center">
  <em>RestTemplate es una clase en el framework de Spring que permite realizar llamadas HTTP de forma sencilla en aplicaciones Java, facilitando la comunicación con servicios web. Es comúnmente utilizada para interactuar con APIs REST y realizar operaciones como obtener datos, enviar datos, actualizar recursos y eliminar recursos en un servidor remoto.</em>
</p>

### Podriamos hacer esto para traer todos los posts
````java
@Service
public class PostRestClient {
    RestTemplate restTemplate = new RestTemplate();

    @Value("${variable.de.entorno}")
    private String HOST; 

    public ResponseEntity<List<Post>> getPosts(){
        return ResponseEntity.ok(Arrays.asList(restTemplate.getForEntity(HOST, Post[].class)));
    }
    public ResponseEntity<Post> getPost(Long id){
        return restTemplate.getForEntity(HOST + "/"+ id, Post.class);
    }
}
````

### Y esto si queremos traer uno solo 
````java
    public ResponseEntity<Post> getPost(Long id){
        return ResponseEntity.ok(restTemplate.getForEntity(HOST + "/"+ id, Post.class));
    }
````

