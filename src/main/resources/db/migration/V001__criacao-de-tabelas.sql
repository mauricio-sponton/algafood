create table cozinha(
id bigint not null auto_increment,
nome varchar(60) not null,
  primary key (id)
) engine=InnoDB default charset=utf8;

create table forma_pagamento(
id bigint not null auto_increment, 
descricao varchar(60) not null,

primary key (id)
) engine=InnoDB default charset=utf8;

create table estado (
	id bigint not null auto_increment, 
    nome varchar(80) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table cidade (
	id bigint not null auto_increment,
	estado_id bigint not null,
	nome varchar(80) not null,    
	primary key (id),
	constraint fk_cidade_estado foreign key (estado_id) references estado (id)
) engine=InnoDB default charset=utf8;

create table permissao (
id bigint not null auto_increment, 
    nome varchar(100) not null,
    descricao varchar(60) not null,  
    primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo (
id bigint not null auto_increment, 
    nome varchar(100) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo_permissao (
	grupo_id bigint not null,
	permissao_id bigint not null,
	
	primary key (grupo_id, permissao_id),
    constraint fk_grupo_permissao_permissao foreign key (permissao_id) references permissao (id),
	constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo (id)
) engine=InnoDB default charset=utf8;



create table usuario (
	id bigint not null auto_increment,
	nome varchar(100) not null,
    email varchar(60) not null,
    senha varchar(60) not null,
    data_cadastro datetime not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table usuario_grupo (
	usuario_id bigint not null,
	grupo_id bigint not null,
	
	primary key (usuario_id, grupo_id),
    constraint fk_usuario_grupo_grupo foreign key (grupo_id) references grupo (id),
	constraint fk_usuario_grupo_usuario foreign key (usuario_id) references usuario (id)
) engine=InnoDB default charset=utf8;

create table restaurante (
	id bigint not null auto_increment,
	cozinha_id bigint not null,
	nome varchar(80) not null,
	taxa_frete decimal(10,2) not null,
	data_atualizacao datetime not null,
	data_cadastro datetime not null,
	
	endereco_cidade_id bigint,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60),
	
	primary key (id),
    constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinha (id),
    constraint fk_restaurante_cidade foreign key (endereco_cidade_id) references cidade (id)
) engine=InnoDB default charset=utf8;


create table produto (
	id bigint not null auto_increment,
	restaurante_id bigint not null,
	nome varchar(80) not null,
	descricao text not null,
	preco decimal(10,2) not null,
	ativo tinyint(1) not null,
	
	primary key (id),
    constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante (id)
) engine=InnoDB default charset=utf8;



create table pedido(
	 id bigint not null auto_increment,
  subtotal decimal(10,2) not null,
  taxa_frete decimal(10,2) not null,
  valor_total decimal(10,2) not null,

  restaurante_id bigint not null,
  usuario_cliente_id bigint not null,
  forma_pagamento_id bigint not null,
  
  endereco_cidade_id bigint(20) not null,
  endereco_cep varchar(9) not null,
  endereco_logradouro varchar(100) not null,
  endereco_numero varchar(20) not null,
  endereco_complemento varchar(60) null,
  endereco_bairro varchar(60) not null,
  
  status varchar(10) not null,
  data_criacao datetime not null,
  data_confirmacao datetime null,
  data_cancelamento datetime null,
  data_entrega datetime null,

  primary key (id),
  
  constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id),
  constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario (id),
  constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id)
    
) engine=InnoDB default charset=utf8;

create table item_pedido (
  id bigint not null auto_increment,
  quantidade smallint(6) not null,
  preco_unitario decimal(10,2) not null,
  preco_total decimal(10,2) not null,
  observacao varchar(255) null,
  pedido_id bigint not null,
  produto_id bigint not null,
  
  primary key (id),
  unique key uk_item_pedido_produto (pedido_id, produto_id),

  constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id),
  constraint fk_item_pedido_produto foreign key (produto_id) references produto (id)
) engine=InnoDB default charset=utf8;

create table restaurante_forma_pagamento (
	restaurante_id bigint not null,
	forma_pagamento_id bigint not null,
	
	primary key (restaurante_id, forma_pagamento_id),
    
	constraint fk_rest_forma_pagto_forma_pagto foreign key (forma_pagamento_id) references forma_pagamento (id),
	constraint fk_rest_forma_pagto_restaurante foreign key (restaurante_id) references restaurante (id)
) engine=InnoDB default charset=utf8;








