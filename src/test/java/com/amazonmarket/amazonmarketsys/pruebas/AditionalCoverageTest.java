package com.amazonmarket.amazonmarketsys.pruebas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.*;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.openxava.actions.ViewBaseAction;
import org.openxava.jpa.XPersistence;
import org.openxava.view.View;
import com.amazonmarket.amazonmarketsys.actions.*;
import com.amazonmarket.amazonmarketsys.model.catalogo.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;
import com.amazonmarket.amazonmarketsys.model.personas.*;
import com.amazonmarket.amazonmarketsys.model.reportes.*;
import com.amazonmarket.amazonmarketsys.model.inventario.*;
import com.amazonmarket.amazonmarketsys.model.caja.*;
import com.amazonmarket.amazonmarketsys.model.ventas.*;
import com.amazonmarket.amazonmarketsys.model.promociones.*;
import com.amazonmarket.amazonmarketsys.model.seguridad.*;
import com.amazonmarket.amazonmarketsys.service.*;

public class AditionalCoverageTest {

    @org.junit.jupiter.api.BeforeAll
    public static void setUpAll() {
        XPersistence.setPersistenceUnit("junit");
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        try {
            XPersistence.getManager().clear();
        } catch (Exception e) {
            // Ignore
        }
    }

    private void setActionView(ViewBaseAction action, View view) {
        try {
            action.setView(view);
        } catch (Throwable t) {
            try {
                java.lang.reflect.Field field = ViewBaseAction.class.getDeclaredField("view");
                field.setAccessible(true);
                field.set(action, view);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initActionMessages(org.openxava.actions.BaseAction action) {
        try {
            org.openxava.util.Messages messages = new org.openxava.util.Messages();
            org.openxava.util.Messages errors = new org.openxava.util.Messages();
            
            java.lang.reflect.Field messagesField = org.openxava.actions.BaseAction.class.getDeclaredField("messages");
            messagesField.setAccessible(true);
            messagesField.set(action, messages);
            
            java.lang.reflect.Field errorsField = org.openxava.actions.BaseAction.class.getDeclaredField("errors");
            errorsField.setAccessible(true);
            errorsField.set(action, errors);
        } catch (Exception e) {
            // Ignore
        }
    }

    private Producto createAndPersistProducto(long unique) {
        Categoria cat = new Categoria();
        cat.setNombre("Cat " + unique);
        XPersistence.getManager().persist(cat);

        Marca marca = new Marca();
        marca.setNombre("Marca " + unique);
        XPersistence.getManager().persist(marca);

        UnidadMedida um = new UnidadMedida();
        um.setNombre("Unidad " + unique);
        um.setSimbolo("U" + (unique % 10));
        XPersistence.getManager().persist(um);

        Producto prod = new Producto();
        prod.setCodigo("P-" + (unique % 100000) + "-" + new Random().nextInt(100));
        prod.setNombre("Prod " + unique);
        prod.setPrecioCompra(BigDecimal.TEN);
        prod.setPrecioVenta(BigDecimal.TEN);
        prod.setCategoria(cat);
        prod.setMarca(marca);
        prod.setUnidadMedida(um);
        XPersistence.getManager().persist(prod);
        
        return prod;
    }

    // --- PERSONAS COVERAGE ---
    @Test
    void testClienteGettersSettersAndMethods() {
        Cliente cliente = new Cliente();
        cliente.setId(10L);
        cliente.setDni("12345678");
        cliente.setNombres("Juan");
        cliente.setApellidos("Perez");
        cliente.setTelefono("987654321");
        cliente.setEmail("juan@gmail.com");
        cliente.setDireccion("Lima");
        cliente.setPuntos(15);
        cliente.setFrecuente(true);
        cliente.setActivo(false);

        assertEquals(10L, cliente.getId());
        assertEquals("12345678", cliente.getDni());
        assertEquals("Juan Perez", cliente.getNombreCompleto());
        assertEquals("987654321", cliente.getTelefono());
        assertEquals("juan@gmail.com", cliente.getEmail());
        assertEquals("Lima", cliente.getDireccion());
        assertEquals(15, cliente.getPuntos());
        assertTrue(cliente.isFrecuente());
        assertFalse(cliente.isActivo());
    }

    @Test
    void testProveedorGettersSettersAndMethods() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(0);
        assertNull(proveedor.getCodigoProveedor());

        proveedor.setId(25);
        proveedor.setRuc("20123456789");
        proveedor.setRazonSocial("Test SA");
        proveedor.setNombreComercial("Test");
        proveedor.setContacto("Manager");
        proveedor.setTelefono("5551234");
        proveedor.setCorreo("test@test.com");
        proveedor.setDireccion("Address");
        proveedor.setEstado("INACTIVO");
        proveedor.setObservacion("Obs");

        assertEquals(25, proveedor.getId());
        assertEquals("PROV-000025", proveedor.getCodigoProveedor());
        assertEquals("20123456789", proveedor.getRuc());
        assertEquals("Test SA", proveedor.getRazonSocial());
        assertEquals("Test", proveedor.getNombreComercial());
        assertEquals("Manager", proveedor.getContacto());
        assertEquals("5551234", proveedor.getTelefono());
        assertEquals("test@test.com", proveedor.getCorreo());
        assertEquals("Address", proveedor.getDireccion());
        assertEquals("INACTIVO", proveedor.getEstado());
        assertEquals("Obs", proveedor.getObservacion());
    }

    // --- COMPRAS COVERAGE ---
    @Test
    void testCompraAndDetalleCompra() {
        Compra compra = new Compra();
        compra.setId(0);
        assertNull(compra.getCodigoCompra());

        compra.setId(5);
        assertEquals("COMP-000005", compra.getCodigoCompra());
        compra.setObservacion("Test compra");
        assertEquals("Test compra", compra.getObservacion());

        DetalleCompra detalle = new DetalleCompra();
        detalle.setId(0);
        assertNull(detalle.getCodigoDetalle());
        assertNull(detalle.getCodigoProducto());
        assertNull(detalle.getNombreProducto());

        detalle.setId(3);
        assertEquals("DET-000003", detalle.getCodigoDetalle());

        Producto prod = new Producto();
        prod.setCodigo("P-100");
        prod.setNombre("Prod Test");
        prod.setPrecioCompra(new BigDecimal("15.50"));
        detalle.setProducto(prod);

        assertEquals("P-100", detalle.getCodigoProducto());
        assertEquals("Prod Test", detalle.getNombreProducto());

        detalle.sincronizarDatosProducto();
        assertEquals(new BigDecimal("15.50"), detalle.getPrecioUnitario());

        detalle.setCantidad(4);
        detalle.calcularSubtotal();
        assertEquals(new BigDecimal("62.00"), detalle.getSubtotal());

        compra.getDetalles().add(detalle);
        compra.calcularTotales();

        assertEquals(new BigDecimal("62.00"), compra.getSubtotal());
        assertEquals(new BigDecimal("62.00"), compra.getTotal());

        compra.prepararDetalles();
        assertSame(compra, detalle.getCompra());
        
        detalle.antesDeGuardar();
        assertEquals(new BigDecimal("62.00"), detalle.getSubtotal());
        
        compra.antesDeGuardar();
        assertEquals(new BigDecimal("62.00"), compra.getTotal());
    }

    // --- REPORTES COVERAGE ---
    @Test
    void testReportesPOJOClasses() {
        ReporteCompraProveedor r1 = new ReporteCompraProveedor();
        Proveedor p = new Proveedor();
        r1.setProveedor(p);
        r1.setCantidadCompras(5);
        r1.setTotalComprado(BigDecimal.TEN);
        r1.setFechaInicio(LocalDate.MIN);
        r1.setFechaFin(LocalDate.MAX);

        assertSame(p, r1.getProveedor());
        assertEquals(5, r1.getCantidadCompras());
        assertEquals(BigDecimal.TEN, r1.getTotalComprado());
        assertEquals(LocalDate.MIN, r1.getFechaInicio());
        assertEquals(LocalDate.MAX, r1.getFechaFin());

        ReporteInventario r2 = new ReporteInventario();
        Producto prod = new Producto();
        r2.setProducto(prod);
        r2.setNombreProducto("N");
        r2.setNumeroLote("L");
        r2.setCantidadActual(10);
        r2.setFechaVencimiento(LocalDate.EPOCH);
        r2.setDiasParaVencer(30L);

        assertSame(prod, r2.getProducto());
        assertEquals("N", r2.getNombreProducto());
        assertEquals("L", r2.getNumeroLote());
        assertEquals(10, r2.getCantidadActual());
        assertEquals(LocalDate.EPOCH, r2.getFechaVencimiento());
        assertEquals(30L, r2.getDiasParaVencer());

        ReporteVentas r3 = new ReporteVentas();
        r3.setProducto(prod);
        r3.setNombreProducto("P");
        r3.setCantidadVendida(8);
        r3.setTotalVendido(BigDecimal.ONE);
        r3.setFechaInicio(LocalDate.EPOCH);
        r3.setFechaFin(LocalDate.EPOCH);

        assertSame(prod, r3.getProducto());
        assertEquals("P", r3.getNombreProducto());
        assertEquals(8, r3.getCantidadVendida());
        assertEquals(BigDecimal.ONE, r3.getTotalVendido());
        assertEquals(LocalDate.EPOCH, r3.getFechaInicio());
        assertEquals(LocalDate.EPOCH, r3.getFechaFin());
    }

    // --- COMPRA SERVICE REAL DB ---
    @Test
    void testCompraServiceDatabaseOperations() {
        long unique = System.nanoTime();
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc(String.valueOf(unique % 100000000000L));
        proveedor.setRazonSocial("Test Proveedor " + unique);
        proveedor.setEstado("ACTIVO");
        
        XPersistence.getManager().persist(proveedor);
        XPersistence.commit();

        Producto prod = createAndPersistProducto(unique);
        XPersistence.commit();

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setFechaCompra(LocalDate.now());
        compra.setNumeroDocumento("D-" + (unique % 100000));

        DetalleCompra det = new DetalleCompra();
        det.setProducto(prod);
        det.setCantidad(1);
        det.setPrecioUnitario(BigDecimal.TEN);
        compra.getDetalles().add(det);

        CompraService service = new CompraService();
        service.registrarCompra(compra);
        XPersistence.commit();

        assertNotEquals(0, compra.getId());
        assertEquals("REGISTRADA", compra.getEstadoCompra());

        compra.setObservacion("Updated");
        service.registrarCompra(compra);
        XPersistence.commit();

        service.anularCompra(compra);
        XPersistence.commit();
        assertEquals("ANULADA", compra.getEstadoCompra());

        assertThrows(IllegalArgumentException.class, () -> service.anularCompra(null));
    }

    // --- REPORTE SERVICE REAL DB ---
    @Test
    void testReporteServiceDatabaseOperations() {
        long unique = System.nanoTime();
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc(String.valueOf(unique % 100000000000L));
        proveedor.setRazonSocial("Proveedor Reporte " + unique);
        proveedor.setEstado("ACTIVO");
        XPersistence.getManager().persist(proveedor);

        Producto prod = createAndPersistProducto(unique);
        XPersistence.commit();

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setFechaCompra(LocalDate.now());
        compra.setNumeroDocumento("C-" + (unique % 100000));
        
        DetalleCompra det = new DetalleCompra();
        det.setProducto(prod);
        det.setCantidad(5);
        det.setPrecioUnitario(BigDecimal.TEN);
        compra.getDetalles().add(det);
        
        CompraService compService = new CompraService();
        compService.registrarCompra(compra);
        XPersistence.commit();

        LoteProducto lote = new LoteProducto();
        lote.setProducto(prod);
        lote.setNumeroLote("L-REP-" + (unique % 100000));
        lote.setCantidadInicial(10);
        lote.setCantidadActual(10);
        lote.setFechaVencimiento(LocalDate.now().plusDays(5));
        XPersistence.getManager().persist(lote);
        XPersistence.commit();

        Caja caja = new Caja();
        caja.setNombre("Caja Report " + unique);
        caja.setEstado(Caja.EstadoCaja.ABIERTA);
        XPersistence.getManager().persist(caja);
        XPersistence.commit();

        Venta venta = new Venta();
        venta.setCaja(caja);
        venta.setComprobante(Comprobante.BOLETA_ELECTRONICA);
        venta.setPorcentajeDescuentoManual(BigDecimal.ZERO);
        
        DetalleVenta detV = new DetalleVenta();
        detV.setProducto(prod);
        detV.setCantidad(2);
        detV.setPrecioUnitario(BigDecimal.TEN);
        venta.getDetalles().add(detV);

        XPersistence.getManager().persist(venta);
        XPersistence.commit();

        ReporteService service = new ReporteService();

        assertTrue(service.totalComprado().compareTo(BigDecimal.ZERO) > 0);
        assertEquals(1, service.comprasPorProveedor(proveedor).size());
        assertEquals(0, service.comprasPorProveedor(null).size());
        assertEquals(1, service.comprasPorPeriodo(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)).size());
        assertEquals(0, service.comprasPorPeriodo(null, LocalDate.now()).size());
        assertTrue(service.proveedoresActivos().size() > 0);
        assertTrue(service.comprasRegistradas().size() > 0);
        assertEquals(0, service.comprasAnuladas().size());
        assertTrue(service.totalVendido().compareTo(BigDecimal.ZERO) > 0);

        List<ReporteVentas> masVendidos = service.productosMasVendidos();
        assertTrue(masVendidos.size() > 0);

        List<ReporteInventario> proxVence = service.productosProximosAVencer(10);
        assertTrue(proxVence.size() > 0);
    }

    // --- ACTIONS COVERAGE ---
    @Test
    void testAplicarDescuentoAction() throws Exception {
        AplicarDescuentoAction action = new AplicarDescuentoAction();
        initActionMessages(action);
        View view = mock(View.class);
        setActionView(action, view);

        when(view.getValue("subtotal")).thenReturn(new BigDecimal("200.00"));
        when(view.getValue("porcentajeDescuentoManual")).thenReturn(new BigDecimal("15.00"));

        action.execute();
        verify(view).setValue("descuentoTotal", new BigDecimal("30.0000"));
        verify(view).setValue("total", new BigDecimal("170.0000"));

        // Error cases
        when(view.getValue("porcentajeDescuentoManual")).thenReturn(new BigDecimal("-5.00"));
        action.execute();
        
        when(view.getValue("subtotal")).thenReturn(null);
        when(view.getValue("porcentajeDescuentoManual")).thenReturn(null);
        action.execute();
    }

    @Test
    void testCalcularTotalVentaAction() throws Exception {
        CalcularTotalVentaAction action = new CalcularTotalVentaAction();
        initActionMessages(action);
        View view = mock(View.class);
        setActionView(action, view);

        when(view.getValue("subtotal")).thenReturn(new BigDecimal("100.00"));
        when(view.getValue("porcentajeDescuentoManual")).thenReturn(new BigDecimal("50.00"));

        action.execute();
        verify(view).setValue("descuentoTotal", new BigDecimal("50.0000"));
        verify(view).setValue("total", new BigDecimal("50.0000"));

        when(view.getValue("subtotal")).thenReturn(null);
        when(view.getValue("porcentajeDescuentoManual")).thenReturn(null);
        action.execute();
    }

    @Test
    void testCerrarCajaAction() throws Exception {
        CerrarCajaAction action = new CerrarCajaAction();
        initActionMessages(action);
        View view = mock(View.class);
        setActionView(action, view);

        action.execute();
        verify(view).setValue("estado", Caja.EstadoCaja.CERRADA);
    }

    @Test
    void testGenerarAlertaVencimientoAction() throws Exception {
        GenerarAlertaVencimientoAction action = new GenerarAlertaVencimientoAction();
        initActionMessages(action);
        View view = mock(View.class);
        setActionView(action, view);

        when(view.getValue("fechaVencimiento")).thenReturn(null);
        action.execute();

        when(view.getValue("fechaVencimiento")).thenReturn(LocalDate.now().minusDays(5));
        action.execute();

        when(view.getValue("fechaVencimiento")).thenReturn(LocalDate.now().plusDays(2));
        action.execute();

        when(view.getValue("fechaVencimiento")).thenReturn(LocalDate.now().plusDays(20));
        action.execute();
    }

    @Test
    void testRegistrarCompraAction() throws Exception {
        long unique = System.nanoTime();
        RegistrarCompraAction action = new RegistrarCompraAction();
        initActionMessages(action);
        View view = mock(View.class);
        setActionView(action, view);

        Compra compra = new Compra();
        when(view.getEntity()).thenReturn(compra);

        // Sin proveedor
        action.execute();

        // Con proveedor sin detalles
        Proveedor prov = new Proveedor();
        prov.setRuc(String.valueOf(unique % 100000000000L));
        prov.setRazonSocial("Proveedor Action " + unique);
        compra.setProveedor(prov);
        action.execute();

        // Con todo
        DetalleCompra det = new DetalleCompra();
        Producto prod = createAndPersistProducto(unique);
        det.setProducto(prod);
        det.setCantidad(2);
        det.setPrecioUnitario(BigDecimal.TEN);
        compra.getDetalles().add(det);
        compra.setNumeroDocumento("F-" + (unique % 100000));
        
        XPersistence.getManager().persist(prov);
        XPersistence.commit();

        action.execute();
        verify(view).setValue("total", new BigDecimal("20.00"));
    }

    // --- PRIVATE CONSTRUCTORS ---
    @Test
    void testUtilityClassesPrivateConstructors() {
        callPrivateConstructor(com.amazonmarket.amazonmarketsys.validators.CajaAbiertaValidator.class);
        callPrivateConstructor(com.amazonmarket.amazonmarketsys.validators.PrecioVentaValidator.class);
        callPrivateConstructor(com.amazonmarket.amazonmarketsys.validators.FechaVencimientoValidator.class);
        callPrivateConstructor(com.amazonmarket.amazonmarketsys.validators.StockDisponibleValidator.class);
        callPrivateConstructor(com.amazonmarket.amazonmarketsys.calculators.TotalVentaCalculator.class);
        callPrivateConstructor(com.amazonmarket.amazonmarketsys.calculators.DiasParaVencerCalculator.class);
        callPrivateConstructor(com.amazonmarket.amazonmarketsys.calculators.StockActualCalculator.class);
    }

    private void callPrivateConstructor(Class<?> clazz) {
        try {
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (Exception e) {
            // Ignore
        }
    }

    // --- INVENTARIO MODEL COVERAGE ---
    @Test
    void testInventarioFisicoModel() {
        InventarioFisico inv = new InventarioFisico();
        inv.setId(1L);
        inv.setCantidadSistema(10);
        inv.setCantidadFisica(12);
        inv.setResponsable("Juan");
        inv.setObservacion("Obs");
        inv.setFechaConteo(null);

        inv.calcularDiferencia();

        assertEquals(1L, inv.getId());
        assertEquals(10, inv.getCantidadSistema());
        assertEquals(12, inv.getCantidadFisica());
        assertEquals(2, inv.getDiferencia());
        assertEquals("Juan", inv.getResponsable());
        assertEquals("Obs", inv.getObservacion());
        assertNotNull(inv.getFechaConteo());
    }

    @Test
    void testMermaModel() {
        Merma merma = new Merma();
        merma.setId(1L);
        Producto prod = new Producto();
        LoteProducto lote = new LoteProducto();
        merma.setProducto(prod);
        merma.setLote(lote);
        merma.setMotivo(Merma.MotivoMerma.VENCIMIENTO);
        merma.setCantidad(5);
        merma.setResponsable("Pedro");
        merma.setObservacion("Obs");

        assertEquals(1L, merma.getId());
        assertSame(prod, merma.getProducto());
        assertSame(lote, merma.getLote());
        assertEquals(Merma.MotivoMerma.VENCIMIENTO, merma.getMotivo());
        assertEquals(5, merma.getCantidad());
        assertEquals("Pedro", merma.getResponsable());
        assertEquals("Obs", merma.getObservacion());
        assertNotNull(merma.getFechaRegistro());
    }

    @Test
    void testMovimientoInventarioModel() {
        MovimientoInventario mov = new MovimientoInventario();
        mov.setId(1L);
        Producto prod = new Producto();
        LoteProducto lote = new LoteProducto();
        mov.setProducto(prod);
        mov.setLote(lote);
        mov.setTipoMovimiento(TipoMovimiento.SALIDA_VENTA);
        mov.setCantidad(10);
        mov.setDocumentoReferencia("REF-123");
        mov.setObservacion("Obs");

        assertEquals(1L, mov.getId());
        assertSame(prod, mov.getProducto());
        assertSame(lote, mov.getLote());
        assertEquals(TipoMovimiento.SALIDA_VENTA, mov.getTipoMovimiento());
        assertEquals(10, mov.getCantidad());
        assertEquals("REF-123", mov.getDocumentoReferencia());
        assertEquals("Obs", mov.getObservacion());
        assertNotNull(mov.getFechaMovimiento());
        assertFalse(mov.esIncremento());
    }

    // --- CATALOGO MODEL COVERAGE ---
    @Test
    void testCatalogoClasses() {
        Categoria cat = new Categoria();
        cat.setId(1L);
        cat.setNombre("Lácteos");
        cat.setDescripcion("Desc");
        cat.setActivo(false);
        assertEquals(1L, cat.getId());
        assertEquals("Lácteos", cat.getNombre());
        assertEquals("Desc", cat.getDescripcion());
        assertFalse(cat.isActivo());

        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("Gloria");
        marca.setDescripcion("Desc");
        marca.setActivo(false);
        assertEquals(1L, marca.getId());
        assertEquals("Gloria", marca.getNombre());
        assertEquals("Desc", marca.getDescripcion());
        assertFalse(marca.isActivo());

        UnidadMedida um = new UnidadMedida();
        um.setId(1L);
        um.setNombre("Litro");
        um.setSimbolo("L");
        um.setActivo(false);
        assertEquals(1L, um.getId());
        assertEquals("Litro", um.getNombre());
        assertEquals("L", um.getSimbolo());
        assertFalse(um.isActivo());
        
        Producto prod = new Producto();
        prod.setPrecioCompra(null);
        prod.setPrecioVenta(null);
        assertFalse(prod.tieneMargenValido());
        
        prod.setPrecioCompra(BigDecimal.TEN);
        assertFalse(prod.tieneMargenValido());
        
        prod.setPrecioVenta(BigDecimal.ONE);
        assertFalse(prod.tieneMargenValido());
        
        prod.setPrecioVenta(BigDecimal.TEN);
        assertTrue(prod.tieneMargenValido());
        
        prod.setCodigo(null);
        prod.antesDeGuardar();
        assertNotNull(prod.getCodigo());
        assertTrue(prod.getCodigo().startsWith("PROD-"));
        
        prod.setCodigo("  ");
        prod.antesDeGuardar();
        assertNotNull(prod.getCodigo());
        assertTrue(prod.getCodigo().startsWith("PROD-"));
    }

    // --- VENTAS MODEL COVERAGE ---
    @Test
    void testPagoVentaModel() {
        PagoVenta pago = new PagoVenta();
        pago.setId(1L);
        pago.setMetodoPago(MetodoPago.TARJETA);
        pago.setMonto(BigDecimal.TEN);
        pago.setReferencia("REF-PAGO");
        pago.setFechaPago(LocalDateTime.MIN);
        Venta venta = new Venta();
        pago.setVenta(venta);

        assertEquals(1L, pago.getId());
        assertEquals(MetodoPago.TARJETA, pago.getMetodoPago());
        assertEquals(BigDecimal.TEN, pago.getMonto());
        assertEquals("REF-PAGO", pago.getReferencia());
        assertEquals(LocalDateTime.MIN, pago.getFechaPago());
        assertSame(venta, pago.getVenta());
    }

    // --- PROMOCIONES MODEL COVERAGE ---
    @Test
    void testPromocionestaVigenteVariations() {
        Promocion prom = new Promocion();
        prom.setActiva(false);
        assertFalse(prom.estaVigente());

        prom.setActiva(true);
        prom.setFechaInicio(LocalDate.now().plusDays(1)); // In future
        assertFalse(prom.estaVigente());

        prom.setFechaInicio(LocalDate.now().minusDays(1));
        prom.setFechaFin(LocalDate.now().minusDays(1)); // In past
        assertFalse(prom.estaVigente());

        prom.setFechaFin(LocalDate.now().plusDays(1));
        assertTrue(prom.estaVigente());
        
        ReglaDescuento rd = new ReglaDescuento();
        rd.setId(1L);
        rd.setPromocion(prom);
        rd.setDescripcion("Desc");
        rd.setMontoMinimo(BigDecimal.TEN);
        rd.setPorcentajeDescuento(BigDecimal.ONE);
        rd.setRequiereClienteFrecuente(true);
        rd.setDiasAntesVencimiento(5);
        
        assertEquals(1L, rd.getId());
        assertSame(prom, rd.getPromocion());
        assertEquals("Desc", rd.getDescripcion());
        assertEquals(BigDecimal.TEN, rd.getMontoMinimo());
        assertEquals(BigDecimal.ONE, rd.getPorcentajeDescuento());
        assertTrue(rd.isRequiereClienteFrecuente());
        assertEquals(5, rd.getDiasAntesVencimiento());
    }

    // --- CAJA MODEL COVERAGE ---
    @Test
    void testCajaModels() {
        AperturaCaja ac = new AperturaCaja();
        ac.setId(1L);
        Caja caja = new Caja();
        ac.setCaja(caja);
        ac.setResponsable("Juan");
        ac.setObservacion("Obs");
        ac.setMontoInicial(BigDecimal.TEN);
        
        assertEquals(1L, ac.getId());
        assertSame(caja, ac.getCaja());
        assertEquals("Juan", ac.getResponsable());
        assertEquals("Obs", ac.getObservacion());
        assertEquals(BigDecimal.TEN, ac.getMontoInicial());
        assertNotNull(ac.getFechaApertura());

        CierreCaja cc = new CierreCaja();
        cc.setId(1L);
        cc.setCaja(caja);
        cc.setResponsable("Pedro");
        cc.setObservacion("Obs");
        cc.setTotalVentas(BigDecimal.TEN);
        cc.setTotalIngresos(BigDecimal.ONE);
        cc.setTotalEgresos(BigDecimal.ONE);
        cc.setMontoFinal(BigDecimal.TEN);
        cc.calcularDiferencia();

        assertEquals(1L, cc.getId());
        assertSame(caja, cc.getCaja());
        assertEquals("Pedro", cc.getResponsable());
        assertEquals("Obs", cc.getObservacion());
        assertEquals(BigDecimal.TEN, cc.getTotalVentas());
        assertEquals(BigDecimal.ONE, cc.getTotalIngresos());
        assertEquals(BigDecimal.ONE, cc.getTotalEgresos());
        assertEquals(BigDecimal.TEN, cc.getMontoFinal());
        assertEquals(BigDecimal.ZERO, cc.getDiferencia());
        assertNotNull(cc.getFechaCierre());

        MovimientoCaja mc = new MovimientoCaja();
        mc.setId(1L);
        mc.setCaja(caja);
        mc.setTipoMovimiento(MovimientoCaja.TipoMovimientoCaja.EGRESO);
        mc.setMonto(BigDecimal.TEN);
        mc.setDescripcion("Desc");
        mc.setResponsable("Pedro");

        assertEquals(1L, mc.getId());
        assertSame(caja, mc.getCaja());
        assertEquals(MovimientoCaja.TipoMovimientoCaja.EGRESO, mc.getTipoMovimiento());
        assertEquals(BigDecimal.TEN, mc.getMonto());
        assertEquals("Desc", mc.getDescripcion());
        assertEquals("Pedro", mc.getResponsable());
        assertNotNull(mc.getFechaMovimiento());
    }

    // --- SEGURIDAD MODEL COVERAGE ---
    @Test
    void testSeguridadModels() {
        // Permiso
        Permiso p1 = new Permiso();
        p1.setCodigo("P01");
        p1.setNombre("Permiso 1");
        p1.setModulo("Ventas");
        p1.setNivelRiesgo(Permiso.NivelRiesgo.ALTO);
        p1.setDescripcion("Desc");

        assertTrue(p1.perteneceAlModulo("Ventas"));
        assertTrue(p1.perteneceAlModulo("ventas "));
        assertFalse(p1.perteneceAlModulo("Compras"));
        assertFalse(p1.perteneceAlModulo(null));
        assertTrue(p1.esCritico());
        assertEquals("P01 - Permiso 1", p1.getDescripcionCorta());

        p1.setNivelRiesgo(Permiso.NivelRiesgo.BAJO);
        assertFalse(p1.esCritico());

        // Rol
        Rol rol = new Rol();
        rol.setCodigo("R01");
        rol.setNombre("Rol 1");
        rol.setDescripcion("Desc");
        rol.setRolSistema(true);

        assertEquals("R01 - Rol 1", rol.getDescripcionCorta());
        assertEquals(0, rol.getCantidadPermisos());
        assertFalse(rol.tienePermiso(null));
        assertFalse(rol.tienePermiso(""));

        rol.agregarPermiso(p1);
        rol.agregarPermiso(null);
        assertEquals(1, rol.getCantidadPermisos());
        assertTrue(rol.tienePermiso("P01"));
        assertTrue(rol.tienePermiso("p01 "));

        rol.quitarPermiso(null);
        assertEquals(1, rol.getCantidadPermisos());
        rol.quitarPermiso(p1);
        assertEquals(0, rol.getCantidadPermisos());

        // Usuario
        Usuario u = new Usuario();
        u.setNombres(null);
        u.setApellidos(null);
        assertEquals("", u.getNombreCompleto());

        u.setNombres("  Juan ");
        u.setApellidos("Perez  ");
        assertEquals("Juan Perez", u.getNombreCompleto());

        assertFalse(u.tieneRol(null));
        assertFalse(u.tieneRol(""));
        assertFalse(u.tienePermiso(null));
        assertFalse(u.tienePermiso(""));

        u.agregarRol(null);
        u.agregarRol(rol);
        assertTrue(u.tieneRol("R01"));
        
        rol.agregarPermiso(p1);
        assertTrue(u.tienePermiso("P01"));

        u.quitarRol(null);
        u.quitarRol(rol);
        assertFalse(u.tieneRol("R01"));

        u.bloquear();
        assertFalse(u.puedeAcceder());

        u.desbloquear();
        assertTrue(u.puedeAcceder());

        u.desactivar();
        assertFalse(u.puedeAcceder());

        u.activar();
        assertTrue(u.puedeAcceder());

        u.registrarAcceso();
        assertNotNull(u.getUltimoAcceso());
    }

    @Test
    void testDetalleVentaPrivateMethodsAndEdgeCases() throws Exception {
        DetalleVenta det = new DetalleVenta();
        det.setProducto(null);
        det.sincronizarDatosProducto(); // no-op

        // product with price
        Producto p = new Producto();
        p.setPrecioVenta(BigDecimal.TEN);
        det.setProducto(p);
        det.setPrecioUnitario(null);
        det.sincronizarDatosProducto();
        assertEquals(BigDecimal.TEN, det.getPrecioUnitario());

        // antes de guardar
        det.setPrecioUnitario(null);
        det.antesDeGuardar();
        assertEquals(BigDecimal.TEN, det.getPrecioUnitario());

        // calcularSubtotal when precioUnitario is null
        det.setPrecioUnitario(null);
        det.calcularSubtotal();
        assertEquals(BigDecimal.ZERO, det.getSubtotal());

        // isDescuentoValido
        java.lang.reflect.Method method = DetalleVenta.class.getDeclaredMethod("isDescuentoValido");
        method.setAccessible(true);
        
        // null cases
        det.setDescuento(null);
        assertTrue((Boolean) method.invoke(det));

        det.setDescuento(BigDecimal.ONE);
        det.setPrecioUnitario(null);
        assertTrue((Boolean) method.invoke(det));

        det.setPrecioUnitario(BigDecimal.TEN);
        det.setCantidad(1);
        det.setDescuento(BigDecimal.ONE);
        assertTrue((Boolean) method.invoke(det));

        det.setDescuento(BigDecimal.valueOf(20));
        assertFalse((Boolean) method.invoke(det));
    }

    @Test
    void testCompraFechaCompraAutoPopulation() {
        Compra compra = new Compra();
        compra.setFechaCompra(null);
        compra.antesDeGuardar();
        assertEquals(LocalDate.now(), compra.getFechaCompra());
    }
}
