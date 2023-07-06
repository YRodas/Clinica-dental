Drop database if exists ClinicaDentalRodas;
Create database ClinicaDentalRodas;

Use ClinicaDentalRodas;

Create table Pacientes(
    codigoPaciente int not null,
    nombresPacientes varchar(50) not null,
    apellidosPaciente varchar(50) not null,
    sexo char not null,
    fechaNacimiento date not null,
    direccionPaciente varchar(100) not null,
    telefonoPersonal varchar(8),
    fechaPrimeraVisita date,
    primary key PK_codigoPaciente (codigoPaciente)
);

Create table Especialidades(
    codigoEspecialidad int not null auto_increment,
    descripcion varchar(100) not null,
    primary key PK_codigoEspecialidad (codigoEspecialidad)
);

Create table Medicamentos(
    codigoMedicamento int not null auto_increment,
    nombreMedicamento varchar(100) not null,
    primary key PK_codigoMedicamento (codigoMedicamento)
);

Create table Doctores(
    numeroColegiado int not null,
    nombresDoctor varchar(50) not null,
    apellidosDoctor varchar(50) not null,
    telefonoContacto varchar(8) not null,
    codigoEspecialidad int not null,
    primary key PK_numeroColegiado (numeroColegiado),
    constraint FK_Doctores_Especialidades foreign key (codigoEspecialidad)
        references Especialidades (codigoEspecialidad)
);

Create table Recetas(
    codigoReceta int not null auto_increment,
    fechaReceta date not null,
    numeroColegiado int not null,
    primary key PK_codigoReceta (codigoReceta),
    constraint FK_Recetas_Doctores foreign key (numeroColegiado)
        references Doctores (numeroColegiado)
);

Create table Citas(
    codigoCita int not null auto_increment,
    fechaCita date not null,
    horaCita time not null,
    tratamiento varchar(150),
    descripCondActual varchar(255) not null,
    codigoPaciente int not null,
    numeroColegiado int not null,
    primary key PK_codigoCita (codigoCita),
    constraint FK_Citas_Pacientes foreign key (codigoPaciente)
        references Pacientes (codigoPaciente),
    constraint FK_Citas_Doctores foreign key (numeroColegiado)
        references Doctores (numeroColegiado)
);

Create table DetalleReceta(
    codigoDetalleReceta int not null auto_increment,
    dosis varchar(100) not null,
    codigoReceta int not null,
    codigoMedicamento int not null,
    primary key PK_codigoDetalleReceta (codigoDetalleReceta),
    constraint FK_DetalleReceta_Recetas foreign key (codigoReceta)
        references Recetas (codigoReceta),
    constraint FK_DetalleReceta_Medicamentos foreign key (codigoMedicamento)
        references Medicamentos (codigoMedicamento)
);

-- ----------------Procedimiento Almacenados------------------
-- --------------Pacientes-------------
-- ----------Agregar-Paciente----------
Delimiter $$
	Create procedure sp_AgregarPaciente (in codigoPaciente int, in nombresPacientes varchar(50), in apellidosPaciente varchar(50), in sexo char,
    fechaNacimiento date, in direccionPaciente varchar(100), in telefonoPersonal varchar(8), in fechaPrimeraVisita date)
		Begin
			Insert into Pacientes (codigoPaciente, nombresPacientes, apellidosPaciente, sexo, fechaNacimiento, direccionPaciente , telefonoPersonal, fechaPrimeraVisita)
				values (codigoPaciente, nombresPacientes, apellidosPaciente, sexo, fechaNacimiento, direccionPaciente , telefonoPersonal, fechaPrimeraVisita);
        end$$
delimiter ;

call sp_AgregarPaciente ('13', 'Yonatan', 'Rodas', 'M', '2003-05-31', '19 Avenida 8 - 10 Zona 11', '85461865', '2022-07-16');

-- ----------Listar Pacientes------------

Delimiter $$
	Create procedure sp_ListarPaciente ()
		Begin
			Select 
				P.codigoPaciente, 
                P.nombresPacientes, 
                P.apellidosPaciente, 
                P.sexo, 
                P.fechaNacimiento, 
                P.direccionPaciente , 
                P.telefonoPersonal, 
                P.fechaPrimeraVisita
			From Pacientes P;
        End$$
delimiter ;

call sp_ListarPaciente();

-- ------------Buscar-Paciente-------------

Delimiter $$
	Create procedure sp_BuscarPaciente(in codPaciente int)
		Begin
			Select
				P.codigoPaciente, 
                P.nombresPacientes, 
                P.apellidosPaciente, 
                P.sexo, 
                P.fechaNacimiento, 
                P.direccionPaciente , 
                P.telefonoPersonal, 
                P.fechaPrimeraVisita
			From Pacientes P
				where codigoPaciente = codPaciente;
		End$$
Delimiter ;

call sp_BuscarPaciente (13);

-- --------------Eliminar-Paciente----------------

Delimiter $$
    Create procedure sp_EliminarPaciente (in codPaciente int)
		Begin
			delete from Pacientes
				where codigoPaciente = codPaciente;
        End$$
Delimiter ;

call sp_EliminarPaciente(13);

-- ----------Editar-Paciente-------------

Delimiter $$
	Create procedure sp_EditarPaciente(in codPaciente int, in nomPacientes varchar(50), in apPaciente varchar(50), in sx char,
    fechaNac date, in dirPaciente varchar(100), in telPersonal varchar(8), in fechaPV date)
		Begin
			Update Pacientes P
				set
					P.nombresPacientes=nomPacientes, 
					P.apellidosPaciente=apPaciente, 
					P.sexo=sx, 
					P.fechaNacimiento=fechaNac, 
					P.direccionPaciente=dirPaciente, 
					P.telefonoPersonal=telPersonal, 
					P.fechaPrimeraVisita=fechaPV
					where codigoPaciente = codPaciente;
		End$$
Delimiter ;

call sp_EditarPaciente ('13', 'Yon', 'Rodas', 'M', '2003-05-31', '19 Avenida 8 - 10 Zona 11', '85461865', '2022-07-16');

-- --------------Especialidades-------------
-- ----------Agregar-Especialidad----------

Delimiter $$
Create procedure sp_AgregarEspecialidad(in codigoEspecialidad int, in descripcion varchar(100))
	Begin
		Insert into Especialidades(codigoEspecialidad, descripcion)
			values(codigoEspecialidad, descripcion);
	end$$
Delimiter ;

call sp_AgregarEspecialidad(13, 'La cirugía que cubre las áreas principales de tratamientos quirúrgicos.');

-- ----------Listar-Especialidades----------

Delimiter $$
	Create procedure sp_ListarEspecialidades()
		Begin
			Select
				E.codigoEspecialidad,
				E.descripcion
			from Especialidades E;
		End$$
Delimiter ;

call sp_ListarEspecialidades();

-- ----------Buscar-Especialidad----------

Delimiter $$
	Create procedure sp_BuscarEspecialidad(in codEspecialidad int)
		Begin
			Select
				E.codigoEspecialidad,
				E.descripcion
			from Especialidades E
				where codigoEspecialidad = codEspecialidad;
		End$$
Delimiter ;

call sp_BuscarEspecialidad(13);

-- ----------Eliminar-Especialidad----------

Delimiter $$
	Create procedure sp_EliminarEspecialidad(in codEspecialidad int)
		Begin
			delete from Especialidades
				where codigoEspecialidad = codEspecialidad;
		End$$
Delimiter ;

call sp_EliminarEspecialidad(13);

-- ----------Editar-Especialidad----------

Delimiter $$
	Create procedure sp_EditarEspecialidad(in codEspecialidad int, in descr varchar(100))
		Begin
			Update Especialidades E
				set
                    E.descripcion = descr
					where codigoEspecialidad = codEspecialidad;
		End$$
Delimiter ;

call sp_EditarEspecialidad(13, 'La Cirugía Plástica se basa en la reconstrucción anatómica funcional y estética.');

-- --------------Medicamentos-------------
-- ----------Agregar-Medicamento----------

Delimiter $$
Create procedure sp_AgregarMedicamento(in codigoMedicamento int, in nombreMedicamento varchar(100))
	Begin
		Insert into Medicamentos(codigoMedicamento, nombreMedicamento)
			values(codigoMedicamento, nombreMedicamento);
	end$$
Delimiter ;

call sp_AgregarMedicamento(13, 'Omeprazol');

-- ----------Listar-Medicamentos----------

Delimiter $$
	Create procedure sp_ListarMedicamentos()
		Begin
			Select
				M.codigoMedicamento,
				M.nombreMedicamento
			from Medicamentos M;
		End$$
Delimiter ;

call sp_ListarMedicamentos();

-- ----------Buscar-Medicamento----------

Delimiter $$
	Create procedure sp_BuscarMedicamento(in codMedicamento int)
		Begin
			Select
				M.codigoMedicamento,
				M.nombreMedicamento
			from Medicamentos M
				where codigoMedicamento = codMedicamento;
		End$$
Delimiter ;

call sp_BuscarMedicamento(13);

-- ----------Eliminar-Medicamento----------

Delimiter $$
	Create procedure sp_EliminarMedicamento(in codMedicamento int)
		Begin
			delete from Medicamentos
				where codigoMedicamento = codMedicamento;
		End$$
Delimiter ;

call sp_EliminarMedicamento(13);

-- ----------Editar-Medicamento----------

Delimiter $$
	Create procedure sp_EditarMedicamento(in codMedicamento int, in nomMed varchar(100))
		Begin
			Update Medicamentos M
				set
                    M.nombreMedicamento = nomMed
					where codigoMedicamento = codMedicamento;
		End$$
Delimiter ;

call sp_EditarMedicamento(13, 'Ramipril');

-- --------------Doctores-------------
-- ----------Agregar-Doctor----------
Delimiter $$
	Create procedure sp_AgregarDoctor (in numeroColegiado int, in nombresDoctor varchar(50), in apellidosDoctor varchar(50), in telefonoContacto varchar(8), in codigoEspecialidad int)
		Begin
			Insert into Doctores (numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto, codigoEspecialidad)
				values (numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto, codigoEspecialidad);
        end$$
delimiter ;

call sp_AgregarDoctor (50, 'Kevin', 'Ruiz', '59355649', 13);

-- ----------Listar Doctores------------

Delimiter $$
	Create procedure sp_ListarDoctores ()
		Begin
			Select 
				D.numeroColegiado, 
                D.nombresDoctor, 
                D.apellidosDoctor, 
                D.telefonoContacto,
				D.codigoEspecialidad
			From Doctores D;
        End$$
delimiter ;

call sp_ListarDoctores();

-- ------------Buscar-Doctor-------------

Delimiter $$
	Create procedure sp_BuscarDoctor(in numCol int)
		Begin
			Select
				D.numeroColegiado, 
                D.nombresDoctor, 
                D.apellidosDoctor, 
                D.telefonoContacto,
				D.codigoEspecialidad
			From Doctores D
				where numeroColegiado = numCol;
		End$$
Delimiter ;

call sp_BuscarDoctor(50);

-- --------------Eliminar-Doctor----------------

Delimiter $$
    Create procedure sp_EliminarDoctor (in numCol int)
		Begin
			delete from Doctores
				where numeroColegiado = numCol;
        End$$
Delimiter ;

call sp_EliminarDoctor(50);

-- ----------Editar-Doctor-------------

Delimiter $$
	Create procedure sp_EditarDoctor(in numCol int, in nomDoctores varchar(50), in apDoctor varchar(50), in teleCont varchar(8),
    codEspe int)
		Begin
			Update Doctores D
				set
					D.nombresDoctor=nomDoctores, 
					D.apellidosDoctor=apDoctor, 
					D.telefonoContacto=teleCont, 
					D.codigoEspecialidad=codEspe
					where numeroColegiado = numCol;
		End$$
Delimiter ;

call sp_EditarDoctor (50, 'Carlos', 'Lopez', 41518951, 13);
