import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Métodos estáticos para las actividades de la práctica de Java I/O. */
public final class UtilidadesIO {
    private static final Path MENSAJE = Path.of("datos", "mensaje.txt");
    private UtilidadesIO() {
        // Evita instanciar una clase de utilería.
    }

    public static void copiarBytes(Path origen, Path destino) throws IOException {
        crearPadre(destino);
        try (FileInputStream entrada = new FileInputStream(origen.toFile());
             FileOutputStream salida = new FileOutputStream(destino.toFile())) {
            int dato;
            while ((dato = entrada.read()) != -1) {
                salida.write(dato);
            }
        }
        System.out.println("Archivo copiado por bytes: " + destino);
    }

    public static void copiarCaracteres(Path origen, Path destino) throws IOException {
        crearPadre(destino);
        try (FileReader entrada = new FileReader(origen.toFile());
             FileWriter salida = new FileWriter(destino.toFile())) {
            int caracter;
            while ((caracter = entrada.read()) != -1) {
                salida.write(caracter);
            }
        }
        System.out.println("Archivo copiado por caracteres: " + destino);
    }

    public static void leerLineas(Path archivo) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo.toFile()))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = lector.readLine()) != null) {
                System.out.printf("%02d: %s%n", numeroLinea++, linea);
            }
        }
    }

    public static void convertirMayusculas(Path origen, Path destino) throws IOException {
        crearPadre(destino);
        try (BufferedReader entrada = new BufferedReader(new FileReader(origen.toFile()));
             PrintWriter salida = new PrintWriter(new FileWriter(destino.toFile()))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                salida.println(linea.toUpperCase(Locale.ROOT));
            }
        }
        System.out.println("Archivo en mayúsculas: " + destino);
    }

    public static void explorarPath(Path ruta) {
        System.out.println("Ruta: " + ruta);
        System.out.println("Archivo: " + ruta.getFileName());
        System.out.println("Padre: " + ruta.getParent());
        System.out.println("Número de elementos: " + ruta.getNameCount());
        System.out.println("Ruta absoluta: " + ruta.toAbsolutePath());
    }

    public static void mostrarInformacionArchivo(Path archivo) throws IOException {
        System.out.println("Existe: " + Files.exists(archivo));
        System.out.println("Es archivo: " + Files.isRegularFile(archivo));
        System.out.println("Se puede leer: " + Files.isReadable(archivo));
        if (Files.exists(archivo)) {
            System.out.println("Tamaño: " + Files.size(archivo) + " bytes");
            System.out.println("Última modificación: " + Files.getLastModifiedTime(archivo));
        }
    }

    public static void crearDirectorioYArchivo(Path directorio, String nombreArchivo) throws IOException {
        Files.createDirectories(directorio);
        Path archivo = directorio.resolve(nombreArchivo);
        if (Files.notExists(archivo)) {
            Files.createFile(archivo);
        }
        System.out.println("Creado o disponible: " + archivo);
    }

    public static void crearTemporales() throws IOException {
        System.out.println("Archivo temporal: " + Files.createTempFile("dsi-", ".tmp"));
        System.out.println("Directorio temporal: " + Files.createTempDirectory("dsi-"));
    }

    /** Ejecuta una actividad guiada o el reporte integrador según el argumento recibido. */
    public static void ejecutar(String[] args) {
        try {
            if (args.length == 0) { mostrarUso(); return; }
            switch (args[0]) {
                case "copiar-bytes": copiarBytes(MENSAJE, Path.of("salida", "copia-bytes.txt")); break;
                case "copiar-caracteres": copiarCaracteres(MENSAJE, Path.of("salida", "copia-caracteres.txt")); break;
                case "leer-lineas": leerLineas(MENSAJE); break;
                case "mayusculas": convertirMayusculas(MENSAJE, Path.of("salida", "mensaje-mayusculas.txt")); break;
                case "explorar-path": explorarPath(MENSAJE); break;
                case "informacion": mostrarInformacionArchivo(MENSAJE); break;
                case "crear-directorios": crearDirectorioYArchivo(Path.of("salida", "reportes", "procesados"), "resultado.txt"); break;
                case "temporales": crearTemporales(); break;
                case "todo": ejecutarActividadesGuiadas(); break;
                default: generarReporte(Path.of(args[0]));
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    private static void ejecutarActividadesGuiadas() throws IOException {
        copiarBytes(MENSAJE, Path.of("salida", "copia-bytes.txt"));
        copiarCaracteres(MENSAJE, Path.of("salida", "copia-caracteres.txt"));
        leerLineas(MENSAJE);
        convertirMayusculas(MENSAJE, Path.of("salida", "mensaje-mayusculas.txt"));
        explorarPath(MENSAJE);
        mostrarInformacionArchivo(MENSAJE);
        crearDirectorioYArchivo(Path.of("salida", "reportes", "procesados"), "resultado.txt");
        crearTemporales();
    }

    private static void generarReporte(Path entrada) throws IOException {
        if (!Files.exists(entrada) || !Files.isRegularFile(entrada)) {
            System.err.println("No existe el archivo de incidencias: " + entrada.toAbsolutePath()); return;
        }
        System.out.println("Nombre: " + entrada.getFileName());
        System.out.println("Ruta absoluta: " + entrada.toAbsolutePath());
        System.out.println("Tamaño: " + Files.size(entrada) + " bytes");
        System.out.println("Última modificación: " + Files.getLastModifiedTime(entrada));
        Path directorioSalida = Path.of("mesa-ayuda", "salida", "reportes");
        Files.createDirectories(directorioSalida);
        Path archivoReporte = directorioSalida.resolve("reporte-incidencias.txt");
        Path archivoAltas = directorioSalida.resolve("incidencias-alta.txt");
        List<Incidencia> altas = new ArrayList<Incidencia>();
        int total = 0, medias = 0, bajas = 0;
        try (BufferedReader lector = Files.newBufferedReader(entrada)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Incidencia incidencia = Incidencia.desde(linea);
                total++;
                switch (incidencia.prioridad) {
                    case "ALTA": altas.add(incidencia); break;
                    case "MEDIA": medias++; break;
                    case "BAJA": bajas++; break;
                    default: System.err.println("Prioridad no reconocida: " + incidencia.prioridad);
                }
            }
        }
        try (PrintWriter reporte = new PrintWriter(Files.newBufferedWriter(archivoReporte));
             PrintWriter reporteAltas = new PrintWriter(Files.newBufferedWriter(archivoAltas))) {
            for (Incidencia incidencia : altas) reporteAltas.println(incidencia.lineaOriginal);
            reporte.println("REPORTE DE INCIDENCIAS"); reporte.println("======================");
            reporte.printf("%nArchivo procesado: %s%n%n", entrada.getFileName());
            reporte.printf("Total de incidencias: %d%n%n", total);
            reporte.printf("Prioridad ALTA: %d%n", altas.size());
            reporte.printf("Prioridad MEDIA: %d%n", medias); reporte.printf("Prioridad BAJA: %d%n%n", bajas);
            reporte.println("INCIDENCIAS DE ALTA PRIORIDAD"); reporte.println("--"); reporte.println();
            for (Incidencia incidencia : altas) reporte.printf("%s - %s%n", incidencia.id, incidencia.descripcion);
        }
        System.out.println("Reportes generados en: " + directorioSalida.toAbsolutePath());
    }

    private static void mostrarUso() {
        System.out.println("Uso: java GeneradorReporteIncidencias <archivo-incidencias>");
        System.out.println("Actividades: copiar-bytes, copiar-caracteres, leer-lineas, mayusculas,");
        System.out.println("explorar-path, informacion, crear-directorios, temporales o todo.");
    }

    private static void crearPadre(Path archivo) throws IOException {
        Path padre = archivo.getParent();
        if (padre != null) {
            Files.createDirectories(padre);
        }
    }

    private static final class Incidencia {
        private final String id, descripcion, prioridad, lineaOriginal;
        private Incidencia(String id, String descripcion, String prioridad, String lineaOriginal) {
            this.id = id; this.descripcion = descripcion; this.prioridad = prioridad; this.lineaOriginal = lineaOriginal;
        }
        private static Incidencia desde(String linea) throws IOException {
            String[] campos = linea.split("\\|", -1);
            if (campos.length != 3 || campos[0].trim().isEmpty() || campos[1].trim().isEmpty() || campos[2].trim().isEmpty()) {
                throw new IOException("Registro de incidencia inválido: " + linea);
            }
            return new Incidencia(campos[0].trim(), campos[1].trim(), campos[2].trim().toUpperCase(Locale.ROOT), linea);
        }
    }
}
