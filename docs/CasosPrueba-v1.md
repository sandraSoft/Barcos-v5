### Casos de prueba “calcularCapacidad”, de Carguero
(Es el 80 % del volumen, y resta 40 si lleva líquidos)

Descripción | Valores de entrada | Resultados esperados
----------- | ------------------ | --------------------
Carguero sin líquidos | Crear un carguero con volumen=100, líquidos=false |	80
Carguero con líquidos | Crear un carguero con volumen=200, líquidos=true | 120
Carguero con volumen menor a 50, con líquidos | Crear un carguero con volumen=30, líquidos=true | 0


### Casos de prueba “calcularCapacidad”, de Velero
(Es el 50 % del volumen, y resta 10 si lleva más de 10 pasajeros)

Descripción | Valores de entrada | Resultados esperados
----------- | ------------------ | --------------------
Velero con menos de 10 pasajeros | Crear un velero con volumen=100, pasajeros=8 | 50
Velero con más de 10 pasajeros | Crear un velero con volumen=200, pasajeros=20 | 90
Velero con volumen menor a 20 y más de 10 pasajeros | Crear un velero con volumen=10, pasajeros=12 | 0


### Casos de prueba “adicionarBarco”, de Puerto
(Se crea el Puerto enviándole como repositorio “ListaBarcos” – que es una lista en memoria, inicialmente vacía)

Descripción | Valores de entrada | Resultados esperados
----------- | ------------------ | --------------------
Adicionar un barco (velero) con datos correctos	 | * matrícula=123, nacionalidad=colombiana, volumen=200, tipo=v, pasajeros=10, líquidos=true | * ANTES de llamar al método: validar matrícula es true <br/>* DESPUÉS: validar matrícula es false
Ingresar un barco (velero) con matrícula repetida | * matrícula=245, nacionalidad=peruana, volumen=100, tipo=v, pasajeros=5, líquidos=false <br/>* matrícula=245, nacionalidad=peruana, volumen=100, tipo=v, pasajeros=5, líquidos=false | * ANTES de llamar a los métodos: validar matrícula es true <br/>* DESPUÉS: -validar matrícula es false -lanza BarcoException
Que no permita ingresar un barco con volumen negativo | * matrícula=789, nacionalidad=italiano, volumen=-79, tipo=v, pasajeros=10, líquidos=true | -validar matrícula es true -lanza BarcoException
Que no permita ingresar un barco con volumen mayor a lo permitido | * matrícula=003, nacionalidad=canadiense, volumen=1500,  tipo=c, pasajeros=30, líquidos=false | -validar matrícula es true -lanza BarcoException


### Casos de prueba “calcularCapacidadTotal”, de Puerto
(Se crea el Puerto enviándole como repositorio “ListaBarcos” – que es una lista en memoria, inicialmente vacía)

Descripción | Valores de entrada | Resultados esperados
----------- | ------------------ | --------------------
Cuando no hay barcos | (ninguno) | 0
Con varios barcos: un velero con menos de 10 pasajeros y otro con más de 10, un carguero con líquidos y otro que no. | * Adicionar un velero con volumen=100 y pasajeros=8 * Adicionar un velero con volumen=150 y pasajeros=15 * Adicionar un carguero con volumen=500 y líquidos=true * Adicionar un velero con volumen=250 y líquidos=false | 675

