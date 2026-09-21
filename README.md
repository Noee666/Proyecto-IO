# Práctica de Java I/O


```bash
javac -d bin src/UtilidadesIO.java src/GeneradorReporteIncidencias.java
java -cp bin GeneradorReporteIncidencias mesa-ayuda/entrada/incidencias.txt
```

Actividades individuales: `copiar-bytes`, `copiar-caracteres`, `leer-lineas`,
`mayusculas`, `explorar-path`, `informacion`, `crear-directorios`, `temporales`
y `todo`.

Por ejemplo: `java -cp bin GeneradorReporteIncidencias todo`.

## Respuestas de reflexión

1. Un stream es un flujo secuencial de datos entre una fuente y un destino.
2. Input lee datos; output los escribe.
3. Los bytes son unidades de 8 bits; los caracteres interpretan texto y codificación.
4. `FileInputStream` se usa para datos binarios, como imágenes.
5. `BufferedReader` reduce accesos al sistema y facilita leer líneas.
6. `readLine()` devuelve `null` al llegar al final del archivo.
7. El búfer agrupa operaciones y disminuye la sobrecarga de E/S.
8. La ruta absoluta parte de la raíz; la relativa depende del directorio actual.
9. `Path` representa la ubicación de un archivo o directorio.
10. `Files` ofrece operaciones estáticas para consultar y manipular rutas.
11. `Files.exists()` indica si una ruta existe.
12. `resolve()` une una ruta base con otra parte de ruta.
13. `createDirectories()` crea todos los directorios faltantes.
14. Un glob es un patrón de coincidencia de nombres, por ejemplo `*.txt`.
15. Un channel mueve datos y un buffer es el espacio temporal donde se almacenan.
16. `BufferedReader`.
17. `FileInputStream` y `FileOutputStream`.
18. `Path`, `Files`, `BufferedReader`, `PrintWriter` y directorios con `createDirectories()`.
