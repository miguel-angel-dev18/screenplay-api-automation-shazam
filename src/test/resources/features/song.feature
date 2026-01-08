@ShazamApi
Feature: Shazam Songs API
  Como un desarrollador interesado en la música
  Quiero consultar y detectar canciones mediante la API de Shazam
  Para obtener información detallada de pistas musicales

  @GetDetails @Positive
  Scenario: Obtener detalles de una canción de forma exitosa
    Given el usuario configura el servicio de Shazam
    When consulta los detalles de una canción
    Then la respuesta debe ser exitosa
    And la respuesta debe coincidir con el esquema de respuesta de la canción
    And la respuesta debe contener los headers esperados
    And el tiempo de respuesta debe ser menor a 10000 milisegundos

  @GetDetails @Negative
  Scenario: Intentar consultar canción con API Key inválida
    Given el usuario configura el servicio de Shazam sin credenciales validas
    When consulta los detalles de una canción sin autorización
    Then el servicio debe responder con codigo 401
    And el mensaje de error debe indicar acceso no autorizado

  @DetectSong @Positive
  Scenario: Detectar una canción enviando un payload de texto
    Given el usuario configura el servicio de Shazam
    When intenta detectar una canción con el payload "Contenido de prueba para detección"
    Then el servicio debe responder con codigo 204

  @DetectSong @Negative
  Scenario: Intentar detectar canción con un payload vacío
    Given el usuario configura el servicio de Shazam
    When intenta detectar una canción con el payload ""
    Then el servicio debe responder con codigo 204