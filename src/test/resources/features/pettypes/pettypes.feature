Feature: Gestionar tipos de mascotas

  Scenario: Listar tipos de mascotas
    When el cliente realiza una peticion GET a "api/pettypes"
    Then el servidor debe de responder con un status 200
    And el cuerpo de la respuesta debe ser una Lista de tipos de mascotas

  Scenario: Listar un tipo de mascota por id 4
    When el cliente realiza una peticion GET a "api/pettypes/4"
    Then el servidor debe de responder con un status 200

  Scenario: Listar un tipo de mascota por id 5
    When el cliente realiza una peticion GET a "api/pettypes/5"
    Then el servidor debe de responder con un status 200

  Scenario: Listar un tipo de mascota por id 6
    When el cliente realiza una peticion GET a "api/pettypes/6"
    Then el servidor debe de responder con un status 200

  Scenario Outline: Obtener mascota por <id>
    When el cliente realiza una peticion GET a <path>
    Then el servidor debe de responder con un status <statusCode>
    And el cuerpo de la respuesta contiene la propiedad id con el valor <id>
    And el cuerpo de la respuesta contiene la propiedad name con el valor <name>
    Examples:
      | path             | statusCode | id | name      |
      | "api/pettypes/4" | 200        | 4  | "snake"   |
      | "api/pettypes/5" | 200        | 5  | "bird"    |
      | "api/pettypes/6" | 200        | 6  | "hamster" |

#  Scenario: Registrar tipo de mascota
#    Given el cliente configura el recurso "api/pettypes" con los datos
#      """
#        {
#          "name": "Rabbit"
#        }
#      """
#    When el cliente registra el nuevo tipo de mascota
#    Then el servidor debe de responder con un status 201
#    And el cuerpo de la respuesta debe contener los detalles del nuevo tipo de mascota registrado

  @actualizarTipoMascota
  Scenario: Actualizar un tipo de mascota
    Given el cliente configura el recurso "api/pettypes/{id}" con id 2 usando los datos
      """
        {
          "id": 0,
          "name": "abc"
        }
      """
    When el cliente actualiza el tipo de mascota
    Then el servidor debe de responder con un status 204
    And el cuerpo de la respuesta debe estar vacio

  @eliminarTipoMascota
  Scenario: Eliminar un tipo de mascota
    Given el cliente configura el recurso "api/pettypes/{id}" con id 3
    When el cliente elimina el tipo de mascota
    Then el servidor debe de responder con un status 204
    And el cuerpo de la respuesta debe estar vacio