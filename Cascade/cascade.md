# Cascade

<p>
    <em>Es una propiedad la cual afectara en "cascada" es decir que afectara a todas las entidades que se encuentren contenidas dentro del mismo</em>
</p>
<em>Ejemplo:</em>

```java
@Data
@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private LocalDateTime fecha;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_detalle")
    private List<DetallesFactura> detalles = new ArrayList<>();

    private BigDecimal total;
}

```

# Explicacion
<em>Los detalles dentro de la factura seran actualizados o insertados, sin importar si tienen un repositorio o no, jpa-spring del repo de factura detectara automaticamente la entidad de detalle y su tabla y los insertara/actualizara.</em>
<br>
<br>
<em>Es importante mencionar que hay mas de un tipo de cascade pero ante la duda le clavan all y listo. Las distintas tipos de accion en la cascade son PERSIST, MERGE, REMOVE, DETACHED, y REFRESH, cualquier cosa le preguntan a GPT pero basicamente es todo relacionado con lo que puedo o no hacer el cascade al actualizar/insertar informacion.</em>