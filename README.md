# BDA_LAB1

Prerequisitos de instalacion:

-Postgres 16

-JDK 17

-Visual Studio Code version mas actual

-Intellij version mas actual

-Node js 20.16.0

Intrucciones de instalacion:

Una vez descargado/clonado el repositorio siga los siguientes pasos:

1.-Abra pgadmin

2.-Abra una consola en la direccion dentro de Database y ejecute el siguiente comando: "psql -U El usuario con el que instalo postgres -h localhost -p el puerto con cual instalo postgres -f dbCreate.sql"

3.-Ahora en la misma consola ejecute el siguiente comando para hacer el rellenado de datos:"psql -U El usuario con el que instalo postgres -h localhost -d lab2bda -f dump.sql"

4.-Abra intellij idea y abra la carpeta ""BackendLab1"

5.-Ahora modifique el aplicaction properties:

    -DB_URL=jdbc:postgresql://localhost:el puerto con cual instalo postgres/lab1bda

    -USER=El usuario con el que instalo postgres

    -PASSWORD=La contraseña con la que instalo postgres

6.- Ahora corra el archivo BackendLab1Application presionando el boton de play

7.- Luego dentro de Visual Studio Code abra "FrontEndLab1"

8.- Acceda a este link con el correo usach https://drive.google.com/drive/folders/1A9UYUIQ9KQy_Yf2CQyWUI7lB-p42thyZ?usp=sharing descargue el archivo .env y peguelo en "FrontEndLab1"  y renombrelo a ".env"

9.- Abra un terminal y ejecute "npm install"

10.- Luego ejecute "npm audit fix" y "npm install axios"

11.- Haga correr el frontend en la consola anote "npm run dev" y presione con ctrl + click izquierdo http://localhost:3000/ o este link



