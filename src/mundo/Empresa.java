package mundo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author alejandra
 */
public class Empresa {
    protected int numerousuarios;
    protected int numeroproductos;
    protected Inventario inventario;
    protected Usuario[] usuarioss;
    protected Producto[] productos;
    protected Caja caja;
    protected ArrayList<Cliente>clientes;
    protected ArrayList<Promocion>promociones;
    protected ArrayList<Venta>ventas;
    protected ArrayList<Abastecimiento>abastecimientos;
    int contadorventas=0;
    boolean ventacreada=false;
    int contadorabastecimientos=0;
    boolean abastecimientocreado=false;
    /////////////////////////////
    Usuario usuarioactual=new Usuario("dfjl","dfg",Usuario.tipoacceso.ALTO,"ramon","dfg",7887654);  
    ///////////////////////////////////////
    
    public Empresa(int numerousuarios, int numeroproductos) {
        this.numerousuarios = numerousuarios;
        this.numeroproductos = numeroproductos;
        usuarioss=new Usuario[this.numerousuarios];
        productos=new Producto[this.numeroproductos];
        clientes=new ArrayList<Cliente>();
        promociones=new ArrayList<Promocion>();
        ventas=new ArrayList<Venta>();
        abastecimientos=new ArrayList<Abastecimiento>();
        caja=new Caja();
    }
    
    public boolean crearyAgregarUsuario(String nombre, String cargo,int identificacion, String usuario, String clave, Usuario.tipoacceso tipoacceso)throws FileNotFoundException,IOException{
        ArrayList lista=new ArrayList();
        boolean retorno=false;
        if(verificaAccesoaMetodo("crearyagregarusuario")==true){
            for(int i=0;i<this.numerousuarios;i++){
                if(usuarioss[i]==null){
                    usuarioss[i]=new Usuario(usuario,clave,tipoacceso,nombre,cargo,identificacion);        
                    lista.add(usuarioss[i]);
                    try{
                        ManejoArchivo.leeArchivo("usuarios");
                    }catch(FileNotFoundException | NoSuchElementException e){
                        ManejoArchivo.crearArchivoyGuardarInfo(lista, "usuarios");
                        retorno=true;
                        break;
                    }
                    ManejoArchivo.agregarDatos(lista, "usuarios");
                    retorno=true;
                    break;
                }else{
                    retorno=false;
                }
            }
        }if(usuarioss[0]==null){
            for(int i=0;i<this.numerousuarios;i++){
                if(usuarioss[i]==null){
                    usuarioss[i]=new Usuario(usuario,clave,tipoacceso,nombre,cargo,identificacion);        
                    lista.add(usuarioss[i]);
                    try{
                        ManejoArchivo.leeArchivo("usuarios");
                    }catch(FileNotFoundException | NoSuchElementException e){
                        ManejoArchivo.crearArchivoyGuardarInfo(lista, "usuarios");
                        retorno=true;
                        break;
                    }
                    ManejoArchivo.agregarDatos(lista, "usuarios");
                    retorno=true;
                    break;
                }else{
                    retorno=false;
                }
            }
        }
        return retorno;
    }        
    
    public boolean eliminarUsuario(String u)throws IOException{
        ArrayList<Usuario> usuario=new ArrayList<Usuario>();
        boolean retorno=false;
        if(verificaAccesoaMetodo("eliminarusuario")==true){
            for(int i=0;i<this.numerousuarios;i++){
                if(usuarioss[i].getUsuario().equalsIgnoreCase(u)){
                    usuarioss[i]=null;
                    usuario=ManejoArchivo.leeArchivo("usuarios");
                    for(int j=0;j<usuario.size();j++){
                        if(usuario.get(j).getUsuario().equals(u)){
                            usuario.remove(j);
                            ManejoArchivo.crearArchivoyGuardarInfo(usuario, "usuarios");
                            break;
                        }
                    }
                    retorno=true;
                    break;
                }
            }
        }    
        return retorno;
    }
    
    public boolean modificaDatoUsuario(String usuario,String tipodedato,String dato)throws IOException{
        boolean retorno=false;
        if(verificaAccesoaMetodo("modificadatousuario")==true){
            for(int i=0;i<this.numerousuarios;i++){
                if(usuarioss[i].getUsuario().equals(usuario)){
                    if(tipodedato.equalsIgnoreCase("usuario")){
                        usuarioss[i].setUsuario(dato);
                        ManejoArchivo.modificaDato("usuarios", tipodedato, dato, usuario);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("clave")){
                        usuarioss[i].setClaveacceso(dato);
                        ManejoArchivo.modificaDato("usuarios", tipodedato, dato, usuario);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("nivel")){
                        Usuario.tipoacceso dat=Usuario.tipoacceso.valueOf(dato);
                        usuarioss[i].setNivelacceso(dat);
                        ManejoArchivo.modificaDato("usuarios", tipodedato, dato, usuario);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("nombre")){
                        usuarioss[i].setNombre(dato);
                        ManejoArchivo.modificaDato("usuarios", tipodedato, dato, usuario);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("cargo")){
                        usuarioss[i].setCargo(dato);
                        ManejoArchivo.modificaDato("usuarios", tipodedato, dato, usuario);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("identificacion")){
                        int dat=Integer.parseInt(dato);
                        usuarioss[i].setIdentificacion(dat);
                        ManejoArchivo.modificaDato("usuarios", tipodedato, dato, usuario);
                        retorno=true;
                        break;
                    }
                }
            }
        }    
        return retorno;
    }
    
    public String ingresarUsuario(String usuario, String clave){
        String retorno="";
        for(int i=0;i<this.numerousuarios;i++){
            if (usuarioss[i].getUsuario().equals(usuario)){
                if(usuarioss[i].getClaveacceso().equals(clave)){
                    usuarioss[i]=usuarioactual;
                }else{
                    retorno= "La clave es incorrecta";
                }
            }else{
                retorno= "El usuario es incorrecto";
            }
        }
        return retorno;
    }
    
    public void cerrarSesion(){
        this.usuarioactual=null;
    }    
    
    public boolean verificaAccesoaMetodo(String metodo){
        boolean retorno=false;
        if(usuarioactual==null){
            retorno=false;
        }else{
            if(this.usuarioactual.getNivelacceso()==Usuario.tipoacceso.ALTO){
                retorno= true;
            }if(this.usuarioactual.getNivelacceso()==Usuario.tipoacceso.MEDIO){
                retorno = !(metodo.equalsIgnoreCase("caja") || metodo.equalsIgnoreCase("estadistica"));
            }if(this.usuarioactual.getNivelacceso()==Usuario.tipoacceso.BAJO){
                retorno = metodo.equalsIgnoreCase("realizarventa") || metodo.equalsIgnoreCase("abastecerproducto") || metodo.equalsIgnoreCase("crearyagregarcliente")
                        || metodo.equalsIgnoreCase("buscarproducto");
            }
        }
        return retorno;
    }
    
    
    //ojo si el retorno es falso respecto a cant productos
    //falta crear el archivo al crear producto
    
    //falta evaluar si ya existe el codigo del producto
    public boolean crearyAgregarProducto(String nombre, String codigo, double precio, int cantmininvt, double iva, String nombreproveedor, double preciocompraproveedor, int cantinicialeninventario)throws IOException{
        ArrayList lista=new ArrayList();
        boolean retorno=false;
        if(verificaAccesoaMetodo("crearyAgregarProducto")==true){
            for(int i=0;i<this.numeroproductos;i++){
                if (productos[i]==null){
                    productos[i]=new Producto(codigo,nombre,cantinicialeninventario,cantmininvt,precio,iva,nombreproveedor,preciocompraproveedor);
                    lista.add(productos[i]);
                    agregaralInventario(nombre,codigo, cantinicialeninventario);
                    try{
                        ManejoArchivo.leeArchivo("productos");
                    }catch(FileNotFoundException | NoSuchElementException e){
                        ManejoArchivo.crearArchivoyGuardarInfo(lista, "productos");
                        retorno=true;
                        break;
                    }
                    ManejoArchivo.agregarDatos(lista, "productos");
                    retorno=true;
                    break;       
                }
            }  
        }
        return retorno;
    }
    
    public boolean modificaDatoProducto(String codigo,String tipodedato,String dato)throws IOException{
        boolean retorno=false;
        if(verificaAccesoaMetodo("modificadatoproducto")==true){
            for(int i=0;i<this.numeroproductos;i++){
                if(productos[i].getCodigo().equals(codigo)){
                    if(tipodedato.equalsIgnoreCase("codigo")){
                        productos[i].setCodigo(dato);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("nombre")){
                        productos[i].setNombre(dato);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("cantinicialdeexistencia")){
                        int dat=Integer.parseInt(dato);
                        productos[i].setCantinicialdeexistencia(dat);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("cantmininventario")){
                        int dat=Integer.parseInt(dato);
                        productos[i].setCantmininvt(dat);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("precio")){
                        double dat=Double.parseDouble(dato);
                        productos[i].setPrecio(dat);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("iva")){
                        double dat=Double.parseDouble(dato);
                        productos[i].setIva(dat);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("nombreprovee")){
                        productos[i].setNombreproveedor(dato);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("preciocompra")){
                        double dat=Double.parseDouble(dato);
                        productos[i].setPreciocompraaproveedor(dat);
                        ManejoArchivo.modificaDato("productos", tipodedato, dato, codigo);
                        retorno=true;
                        break;
                    }
                }
            }
        }    
        return retorno;
    }
    
    public boolean eliminarProducto(String codigo)throws IOException{
        ArrayList<Producto> prod=new ArrayList<Producto>();
        boolean retorno=false;
        if(verificaAccesoaMetodo("eliminarproducto")==true){
            for(int i=0;i<this.numeroproductos;i++){
                if(productos[i]!=null){
                    if (productos[i].getCodigo().equals(codigo)){
                        productos[i]=null;
                        prod=ManejoArchivo.leeArchivo("productos");
                        eliminardelInventario(codigo);
                        for(int j=0;j<prod.size();j++){
                            if(prod.get(j).getCodigo().equals(codigo)){
                                prod.remove(j);
                                ManejoArchivo.crearArchivoyGuardarInfo(prod, "productos");
                                break;
                            }
                        }
                        retorno=true;
                        break;
                    } 
                }     
            }
        }
        return retorno;
    }
    
    public Producto buscarProducto(String codigo){
        Producto retorno=null;
        if(verificaAccesoaMetodo("buscarProducto")==true){
            for(int i=0;i<this.numeroproductos;i++){
                if (productos[i].getCodigo().equals(codigo)){
                    System.out.println(productos[i].toString());//segun interfaz grafica
                    retorno=productos[i];
                }
            }
        }
        return retorno;
    }
    
    public String listaproductos(){
        String retorno="";
        if(verificaAccesoaMetodo("listaproductos")==true){
            for(int i=0;i<this.numeroproductos;i++){
                if(productos[i]!=null){
                   String variable=productos[i].toString();
                   retorno=retorno+variable;
                }
            }
        }
        return retorno;
    }
    
    
    
    public boolean crearyAgregarCliente(String nombre, String apellido, int identificacion)throws IOException{
        boolean retorno=false;
        ArrayList lista=new ArrayList();
        if(verificaAccesoaMetodo("crearyagregarcliente")==true){
            for(int i=0;i<1;i++){
                clientes.add(new Cliente(nombre,apellido,identificacion));
                for(int j=0;j<clientes.size();j++){
                    if(clientes.get(j).getIdentificacion()==identificacion){
                        lista.add(clientes.get(j));
                    }
                }
                try{
                    ManejoArchivo.leeArchivo("clientes");
                }catch(FileNotFoundException | NoSuchElementException e){
                    ManejoArchivo.crearArchivoyGuardarInfo(clientes, "clientes");
                    retorno=true;
                    break;
                }
                ManejoArchivo.agregarDatos(lista, "clientes");
                retorno=true;
                break;
            }
        }
        return retorno;
    }
    
    public boolean modificaDatoCliente(int identificacion,String tipodedato,String dato)throws IOException{
        boolean retorno=false;
        String sujeto=String.valueOf(identificacion);
        if(verificaAccesoaMetodo("modificadatocliente")==true){
            for(int i=0;i<clientes.size();i++){
                if(clientes.get(i).getIdentificacion()==identificacion){
                    if(tipodedato.equalsIgnoreCase("nombre")){
                        clientes.get(i).setNombre(dato);
                        ManejoArchivo.modificaDato("clientes", tipodedato, dato,sujeto);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("apellido")){
                        clientes.get(i).setApellido(dato);
                        ManejoArchivo.modificaDato("clientes", tipodedato, dato, sujeto);
                        retorno=true;
                        break;
                    }if(tipodedato.equalsIgnoreCase("identificacion")){
                        int dat=Integer.parseInt(dato);
                        clientes.get(i).setIdentificacion(dat);
                        ManejoArchivo.modificaDato("clientes", tipodedato, dato, sujeto);
                        retorno=true;
                        break;
                    }   
                }
            }
        }    
        return retorno;
    }

    
    
    ///falta completar para en tiempo real ir comparando la fecha actual con la ingresada por el usuario
    public boolean crearyAgrearPromocion(String codigopromocion,String codigoproducto,double valordescuento,String hastafecha) throws ParseException, IOException{
        boolean retorno=false;
        if(verificaAccesoaMetodo("crearyagregarpromocion")==true){
            for(int i=0;i<this.numeroproductos;i++){
                if(productos[0]==null){
                    retorno=false;
                    break;
                }else{
                    if(productos[i].getCodigo().equals(codigoproducto)){
                        Promocion p=new Promocion(codigopromocion,productos[i].getCodigo(),valordescuento,hastafecha);
                        promociones.add(p);
                        if(p.aplicarPromocion()==true){
                            ArrayList lista=new ArrayList();
                            lista.add(p);
                            try{
                                ManejoArchivo.leeArchivo("promociones");
                            }catch(FileNotFoundException | NoSuchElementException e){
                                ManejoArchivo.crearArchivoyGuardarInfo(lista, "promociones");
                                retorno=true;
                                break;
                            }
                            ManejoArchivo.agregarDatos(lista, "promociones");
                            retorno=true;
                            break;
                        }
                    }
                }    
            }
        }    
        return retorno;
    }
    
    public boolean eliminarPromocion(String codigo)throws IOException{
        ArrayList<Promocion> promocion=new ArrayList<Promocion>();
        boolean retorno=false;
        if(verificaAccesoaMetodo("eliminarpromocion")==true){
            for(int i=0;i<promociones.size();i++){
                if (promociones.get(i).getCodigo().equals(codigo)){
                    promociones.remove(i);
                    promocion=ManejoArchivo.leeArchivo("productos");
                for(int j=0;j<promocion.size();j++){
                    if(promocion.get(j).getCodigo().equals(codigo)){
                        promocion.remove(j);
                        ManejoArchivo.crearArchivoyGuardarInfo(promocion, "promociones");
                        break;
                    }
                }
                    retorno=true;
                    break;
                }
            }
        }
        return retorno;
    }
    
    public String listaPromociones(){
        String retorno="";
        if(verificaAccesoaMetodo("listapromociones")==true){
            for(int i=0;i<promociones.size();i++){
               String variable=promociones.get(i).toString();
               retorno=retorno+variable;
            }
        }
        return retorno;
    }
    
    
    
    public void agregaralInventario(String nombrepro,String codigopro,int cant) throws IOException{
        boolean variable=true;
        Inventario inv=new Inventario(nombrepro, codigopro, cant);
        ArrayList lista=new ArrayList();
        lista.add(inv);
        try{
            ManejoArchivo.leeArchivo("inventario");
        }catch(FileNotFoundException | NoSuchElementException e){
            ManejoArchivo.crearArchivoyGuardarInfo(lista, "inventario");
            variable=false;
        }    
        if(variable==true){
            ManejoArchivo.agregarDatos(lista, "inventario");
        }
    }
    
    public void eliminardelInventario(String codigo) throws IOException{
        ArrayList<Inventario> inventarios=new ArrayList<Inventario>();
        inventarios=ManejoArchivo.leeArchivo("inventario");
        for(int i=0;i<inventarios.size();i++){
            if(inventarios.get(i).getCodigoproducto().equals(codigo)){  
                inventarios.remove(i);
                ManejoArchivo.crearArchivoyGuardarInfo(inventarios, "inventario");
                break;
            }
        }
    }
    
    public String listarInventario() throws IOException{
        String retorno="";
        ArrayList<Inventario> inventarios=new ArrayList<Inventario>();
        inventarios=ManejoArchivo.leeArchivo("inventario");
        for(int i=0;i<inventarios.size();i++){
            String variable=inventarios.get(i).getCodigoproducto()+" "+inventarios.get(i).getNombreproducto()+" "+inventarios.get(i).getCanteninventario()+"\n";
            retorno=retorno+variable;
        }
        return retorno;
    }
    
    
    public boolean realizarVenta(){
        if(verificaAccesoaMetodo("realizarventa")==true){
            File file=new File("C:\\Users\\alejandra\\Documents\\NetBeansProjects\\proyecto.final\\factura.xlsx");
            if(file.exists()){
                file.delete();
            }
            Venta v=new Venta(contadorventas);
            this.ventas.add(v);
            contadorventas++;
            ventacreada=true;
            return true;
        }else{
            return false;
        }
    }
    
    public boolean agregarAlCarrito(Producto p, int cant) throws IOException{
        boolean retorno=false;
        ArrayList<Inventario> inv=new ArrayList<Inventario>();
        inv=ManejoArchivo.leeArchivo("inventario");
        if(ventacreada=true){
            for(int j=0;j<inv.size();j++){
                if(inv.get(j).getCodigoproducto().equals(p.getCodigo())){
                    if(inv.get(j).getCanteninventario()>=cant){
                        for(int i=0;i<ventas.size();i++){
                            if (ventas.get(i).getCodigo()==contadorventas-1){
                                ventas.get(i).agregarAlCarrito(p, cant);
                                retorno=true;
                            }
                        }
                    }  
                }
            }   
        }     
        return retorno;
    }
    
    public boolean agregarAlCarritoXBascula(Producto p) throws IOException{
        boolean retorno=false; 
        int cant=(int) (Math.random() * 10) + 1;
        ArrayList<Inventario> inv=new ArrayList<Inventario>();
        inv=ManejoArchivo.leeArchivo("inventario");
        if(ventacreada=true){
            for(int j=0;j<inv.size();j++){
                if(inv.get(j).getCodigoproducto().equals(p.getCodigo())){
                    if(inv.get(j).getCanteninventario()>=cant){
                        for(int i=0;i<ventas.size();i++){
                            if (ventas.get(i).getCodigo()==contadorventas-1){
                                ventas.get(i).agregarAlCarrito(p, cant);
                                retorno=true;
                            }
                        }
                    }  
                }
            }   
        }     
        return retorno;
    }
    
    public void quitarDelCarrito(Producto p){
        if(ventacreada=true){
            for(int i=0;i<ventas.size();i++){
                if (ventas.get(i).getCodigo()==contadorventas-1){
                    ventas.get(i).quitarDelCarrito(p);
                }
            }
        }  
    }
    
    public void cancelarVenta(){
        if(ventacreada=true){
            for(int i=0;i<ventas.size();i++){
                if (ventas.get(i).getCodigo()==contadorventas-1){
                    ventas.remove(i);
                    contadorventas=contadorventas-1;
                }
            }
        }
    }
    
    public void modificarnombreCliente(String nombre){
        if(ventacreada=true){
            ventas.get(contadorventas-1).setNombre(nombre);
        }
    }
    
    public void modificarIdentidadCliente(int identidad){
        if(ventacreada=true){
            ventas.get(contadorventas-1).setIdentificacion(identidad);
        }
    }
    
    public ArrayList calculaTotal(Venta v){
        ArrayList<Double>totales=new ArrayList<Double>();
        ArrayList<Double> lista=calculaDescuentoxProducto(v);
        for(int i=0;i<v.getProductos().size();i++){
            double total=(v.getProductos().get(i).getPrecio()*v.getCantidades().get(i))-lista.get(i);
            totales.add(total);
        }
        return totales;
    }
    
    public ArrayList calculaDescuentoxProducto(Venta v){
        boolean variable=false;
        ArrayList<Double>descuentos =new ArrayList<Double>();
        for(int i=0;i<v.getProductos().size();i++){
            for(int j=0;j<this.promociones.size();j++){
                if(v.getProductos().get(i).getCodigo().equals(promociones.get(j).getProducto()) && promociones.get(j).getEstavigente()==Promocion.vigencia.ON){
                    double descuento=promociones.get(j).getValorDescuento()*v.getCantidades().get(i);
                    descuentos.add(descuento);
                    variable=true;
                    break;
                }
            }if(variable==false){
                descuentos.add(0.0);
            }
            variable=false;
        }
        return descuentos;
    }
    
    public double calculaIvaTotal(Venta v){
        ArrayList<Double>lista=calculaTotal(v);
        double retorno=0;
        for(int i=0;i<lista.size();i++){
            double variable=(lista.get(i)*v.getProductos().get(i).getIva())/100;
            retorno=retorno+variable;
        }
        return retorno;
    }
    
    public String alarmaProducto(Venta v) throws IOException{
        String retorno="";
        ArrayList<Inventario> inv=new ArrayList<Inventario>();
        ArrayList<Producto> productosvendidos=new ArrayList<Producto>();
        productosvendidos=v.getProductos();
        inv=ManejoArchivo.leeArchivo("inventario");
        for(int i=0;i<productosvendidos.size();i++){
            for(int j=0;j<inv.size();j++){
                if(productosvendidos.get(i).getCodigo().equals(inv.get(j).getCodigoproducto())){
                    if(inv.get(j).getCanteninventario()<=productosvendidos.get(i).getCantmininvt()){
                        String variable=productosvendidos.get(i).getCodigo()+"           "+productosvendidos.get(i).getNombre()+"             "+inv.get(j).getCanteninventario()+"\n";
                        retorno=retorno+variable;
                        break;
                    }   
                }
            }
        }
        return "\nLos siguientes productos generan alarma por cantidad mínima en inventario: \nCÓDIGO     DESCRIPCIÓN    CANT ACTUAL\n"+retorno;
    }

    public boolean terminarVenta() throws IOException{
        boolean retorno=false;
        double totalfactura=0;
        
        if(ventacreada=true){
            //descuenta del inventario
            ArrayList<Inventario> inv=new ArrayList<Inventario>();
            for(int i=0;i<ventas.get(contadorventas-1).getProductos().size();i++){
                String codigo=ventas.get(contadorventas-1).getProductos().get(i).getCodigo();
                int cant=ventas.get(contadorventas-1).getCantidades().get(i);
                inv=ManejoArchivo.leeArchivo("inventario");
                for(int j=0;j<inv.size();j++){
                    if(codigo.equals(inv.get(j).getCodigoproducto())){
                        int cantinventario=inv.get(j).getCanteninventario();
                        int canttotal=cantinventario-cant;
                        String cantidad=Integer.toString(canttotal);
                        ManejoArchivo.modificaDato("inventario", "cantidad", cantidad, codigo);
                        break;
                    }
                } 
            }  
                    
            
            //modifica factura
            
            String file = new String("C:\\Users\\alejandra\\Documents\\NetBeansProjects\\proyecto.final\\plantilla.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            XSSFRow fila = sheet.getRow(1);
            XSSFCell celda = fila.createCell(5);
            celda.setCellValue(ventas.get(contadorventas-1).getCodigo());
            
            XSSFRow fila1 = sheet.getRow(2);
            XSSFCell celda1 = fila1.createCell(5);
            celda1.setCellValue(ventas.get(contadorventas-1).getFecha());
            
            XSSFRow fila2 = sheet.getRow(3);
            XSSFCell celda2 = fila2.createCell(5);
            celda2.setCellValue(ventas.get(contadorventas-1).getFecha());
            
            if(ventas.get(contadorventas-1).getNombre()!=null){
                XSSFRow fila3 = sheet.getRow(4);
                XSSFCell celda3 = fila3.createCell(3);
                celda3.setCellValue(ventas.get(contadorventas-1).getNombre());
            }
            
            if(ventas.get(contadorventas-1).getIdentificacion()!=0){
                XSSFRow fila3 = sheet.getRow(5);
                XSSFCell celda3 = fila3.createCell(3);
                celda3.setCellValue(ventas.get(contadorventas-1).getIdentificacion());
            }
            
            for(int i=0;i<ventas.get(contadorventas-1).getProductos().size();i++){
                XSSFRow fila3 = sheet.getRow(i+7);
                XSSFCell celda3 = fila3.createCell(1);
                celda3.setCellValue(ventas.get(contadorventas-1).getProductos().get(i).getCodigo());
            }
            
            for(int i=0;i<ventas.get(contadorventas-1).getCantidades().size();i++){
                XSSFRow fila3 = sheet.getRow(i+7);
                XSSFCell celda3 = fila3.createCell(2);
                celda3.setCellValue(ventas.get(contadorventas-1).getCantidades().get(i));
            }
            
            for(int i=0;i<ventas.get(contadorventas-1).getProductos().size();i++){
                XSSFRow fila3 = sheet.getRow(i+7);
                XSSFCell celda3 = fila3.createCell(3);
                celda3.setCellValue(ventas.get(contadorventas-1).getProductos().get(i).getNombre());
            }
            
            for(int i=0;i<ventas.get(contadorventas-1).getProductos().size();i++){
                XSSFRow fila3 = sheet.getRow(i+7);
                XSSFCell celda3 = fila3.createCell(4);
                celda3.setCellValue(ventas.get(contadorventas-1).getProductos().get(i).getPrecio());
            }
            
            ArrayList<Double> lista=calculaDescuentoxProducto(ventas.get(contadorventas-1));
            for(int i=0;i<lista.size();i++){
                XSSFRow fila3 = sheet.getRow(i+7);
                XSSFCell celda3 = fila3.createCell(5);
                celda3.setCellValue(lista.get(i));
            }
            
            ArrayList<Double> lista2=calculaTotal(ventas.get(contadorventas-1));
            for(int i=0;i<lista2.size();i++){
                XSSFRow fila3 = sheet.getRow(i+7);
                XSSFCell celda3 = fila3.createCell(6);
                celda3.setCellValue(lista2.get(i));
            }

            ArrayList<Double> lista3=calculaTotal(ventas.get(contadorventas-1));
            double subtotal=0;
            for(int i=0;i<lista3.size();i++){
                double variable=lista3.get(i);
                subtotal=subtotal+variable;
            } 
            XSSFRow fila4 = sheet.getRow(40);
            XSSFCell celda4 = fila4.createCell(6);
            celda4.setCellValue(subtotal);
               
            XSSFRow fila5 = sheet.getRow(41);
            XSSFCell celda5 = fila5.createCell(6);
            celda5.setCellValue(calculaIvaTotal(ventas.get(contadorventas-1)));
            
            ArrayList<Double> lista4=calculaTotal(ventas.get(contadorventas-1));
            for(int i=0;i<lista4.size();i++){
                double variable=lista4.get(i);
                totalfactura=totalfactura+variable;
            } 
            totalfactura=totalfactura+calculaIvaTotal(ventas.get(contadorventas-1));
            XSSFRow fila6 = sheet.getRow(42);
            XSSFCell celda6 = fila6.createCell(6);
            celda6.setCellValue(totalfactura);
            
            fis.close();
            String file2 = new String("C:\\Users\\alejandra\\Documents\\NetBeansProjects\\proyecto.final\\factura.xlsx");
            FileOutputStream output = new FileOutputStream(file2);
            wb.write(output);
            output.close();
            Runtime.getRuntime().exec("cmd /c start "+file2);            
            
            
            //crea archivo de texto con ventas
            ArrayList<Venta> listas=new ArrayList<Venta>();                
            for(int i=0;i<1;i++){
                for(int j=0;j<ventas.size();j++){
                    if(ventas.get(j).getCodigo()==contadorventas-1){
                        ventas.get(i).setTotalventa(totalfactura);
                        listas.add(ventas.get(j));
                    }
                }
                try{
                    ManejoArchivo.leeArchivo("ventas");
                }catch(FileNotFoundException | NoSuchElementException e){
                    ManejoArchivo.crearArchivoyGuardarInfo(ventas, "ventas");
                    retorno=true;
                    break;
                }
                ManejoArchivo.agregarDatos(listas, "ventas");
                retorno=true;
                break;
            }
            
//          clasifica al cliente segun venta
            for(int j=0;j<clientes.size();j++){
                if(ventas.get(contadorventas-1).getIdentificacion()==clientes.get(j).getIdentificacion()){
                    if(totalfactura>=1000000){////se define segun comprador
                        clientes.get(j).setTipocliente(Cliente.tipocliente.ESTRELLA);
                    }
                }
            }
            
            //actualiza caja
            this.caja.actualizarCaja(totalfactura);    

            ventacreada=false;
            
            //alarma producto se termino
            System.out.println(alarmaProducto(ventas.get(contadorventas-1)));
        }
        return retorno;
    }
    
    public String buscaVentaXFecha(String fecha){
        String retorno="";
        if(verificaAccesoaMetodo("buscaventaxfecha")==true){
            for(int i=0;i<ventas.size();i++){
                if(ventas.get(i).getFecha().equals(fecha)){
                    retorno=ventas.get(i).toString();
                }
            }
        }
        return retorno;
    }
    
    public String buscaVentaXTotalventaHasta(double rangohasta){
        String retorno="";
        if(verificaAccesoaMetodo("buscaventaxtotalventahasta")==true){
            for(int i=0;i<ventas.size();i++){
                if(ventas.get(i).getTotalventa()<=rangohasta){
                    retorno=ventas.get(i).toString();
                }
            }
        }
        return retorno;
    }
    
    public String buscaVentaXTotalventaDesde(double rangodesde){
        String retorno="";
        if(verificaAccesoaMetodo("buscaventaxtotalventadesde")==true){
            for(int i=0;i<ventas.size();i++){
                if(ventas.get(i).getTotalventa()>=rangodesde){
                    retorno=ventas.get(i).toString();
                }
            }
        }
        return retorno;
    }
    
    public String buscaVentaXFechayTotalventaDesde(String fecha,double rangodesde){
        String retorno="";
        if(verificaAccesoaMetodo("buscaventaxfechaytotalventadesde")==true){
            for(int i=0;i<ventas.size();i++){
                if(ventas.get(i).getTotalventa()>=rangodesde && ventas.get(i).getFecha().equals(fecha)){
                    retorno=ventas.get(i).toString();
                }
            }
        }
        return retorno;
    }
    
    public String buscaVentaXFechayTotalventaHasta(String fecha,double rangohasta){
        String retorno="";
        if(verificaAccesoaMetodo("buscaventaxfechaytotalventahasta")==true){
            for(int i=0;i<ventas.size();i++){
                if(ventas.get(i).getTotalventa()<=rangohasta && ventas.get(i).getFecha().equals(fecha)){
                    retorno=ventas.get(i).toString();
                }
            }
        }
        return retorno;
    }
    
    public String listaVentas(){
        String retorno="";
        if(verificaAccesoaMetodo("listaventas")==true){
            for(int i=0;i<ventas.size();i++){
               String variable=ventas.get(i).toString();
               retorno=retorno+variable;
            }
        }
        return retorno;
    }
    
    
 
    public String listaSegunTransacion(String transaccion){
        String retorno="";
        if(verificaAccesoaMetodo("listaseguntransaccion")==true){
            if(transaccion.equals("ventas")){
                for(int i=0;i<ventas.size();i++){
                    String variable=ventas.get(i).toString();
                    retorno=retorno+variable;
                }
            }if(transaccion.equals("abastecimientos")){
                for(int i=0;i<abastecimientos.size();i++){
                    String variable=abastecimientos.get(i).toString();
                    retorno=retorno+variable;
                }
            }
        }    
        return retorno;    
    }
    
    
    public boolean resetCaja(){
        boolean retorno=false;
        if(verificaAccesoaMetodo("resetcaja")==true){ 
           caja.resetCaja();
           retorno=true;
       }
        return retorno;
    }
    
    public String darCaja(){
        String retorno=" No tiene el nivel de acceso para realizar esta operación";
        if(verificaAccesoaMetodo("darcaja")==true){ 
           retorno=Double.toString(caja.darCaja());
        }
        return retorno;
    }
    
}
