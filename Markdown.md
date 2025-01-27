# Documentação do Projeto - Gestão de Itens com .NET

## Objetivo
Criar uma REST API utilizando .NET com conexão a uma base de dados Oracle, para gestão de itens, categorias e vendas. Este sistema será integrado com outro programa em Spring Boot.

---

## Estrutura de Dados

### **Item**
- **Atributos**:
  - `id` (int): Identificador único.
  - `name` (string): Nome do item.
  - `description` (string): Descrição do item.
  - `price` (decimal): Preço do item.
  - `categoryId` (int): Categoria associada.
  - `available` (bool): Indica se o item está disponível para venda.
  - `sale` (Sale?): Detalhes da venda (nulo caso não vendido).

### **Category**
- **Atributos**:
  - `id` (int): Identificador único.
  - `description` (string): Descrição da categoria.
  - `items` (Collection<Item>): Lista de itens associados.

### **Sale**
- **Atributos**:
  - `itemId` (int): Identificador do item vendido.
  - `saleDate` (datetime): Data da venda.
  - `salePrice` (decimal): Valor da venda.

---

## Funcionalidades

### **Endpoints**
#### **Item**
- **POST /items**: Criar um novo item.
- **PUT /items/{id}**: Atualizar um item pelo ID.
- **GET /items/{id}**: Consultar um item pelo ID.
- **GET /items**: Listar todos os itens.
- **DELETE /items/{id}**: Excluir um item pelo ID.

#### **Category**
- **POST /categories**: Criar uma nova categoria.
- **PUT /categories/{id}**: Atualizar uma categoria pelo ID.
- **GET /categories**: Listar todas as categorias.
- **GET /categories/{id}/items**: Listar itens por categoria.
- **DELETE /categories/{id}**: Excluir uma categoria caso não haja itens associados.

#### **Sales**
- **POST /sales**: Registrar uma venda (trigger automático ao marcar `available = false` em um item).
- **GET /sales**: Listar todas as vendas.
- **GET /sales?beforeDate=yyyy-mm-dd**: Listar vendas antes de uma data específica.
- **GET /sales?minPrice=value**: Listar vendas acima de um certo preço.

#### **Estatísticas**
- **GET /statistics**: Retornar estatísticas como:
  - Total de vendas.
  - Total de itens.
  - Itens por categoria.
  - Vendas por categoria.
  - Percentagem de itens vendidos.

---

## Regras de Negócio

1. **Gestão de Itens**:
   - Cada item deve pertencer a uma categoria.
   - Um item só pode ser deletado se não tiver uma venda associada.
   - Ao criar ou atualizar um item, deve ser validado se a categoria existe.

2. **Gestão de Categorias**:
   - Uma categoria só pode ser deletada se não houver itens associados.
   - Itens de uma categoria deletada devem ser transferidos antes da exclusão.

3. **Gestão de Vendas**:
   - Uma venda é registrada automaticamente ao marcar `available = false` em um item.
   - Após uma venda, o atributo `available` do item deve ser atualizado para `false`.

4. **Estatísticas**:
   - Cálculo dinâmico do número de vendas, itens, vendas por categoria e percentagens de itens vendidos.

---


## Tecnologias Utilizadas
- **Framework**: .NET 6 ou superior.
- **Banco de Dados**: Oracle.
- **Ferramentas de Integração**: Swagger para documentação da API, Postman para testes.
- **Padrões de Design**:
  - Repositório para acesso a dados.
  - Serviço para lógica de negócios.
  - DTOs para transferência de dados.

---