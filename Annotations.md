# Anotações

### SOLID Principles

#### Single Responsible Principle (SRP)

`SingleResponsiblePrinciple.java`
 
Indica que uma classe deve ter apenas uma responsabilidade. Caso isso não ocorra, pode gerar um "GOD Object", ou seja, uma classe que faz tudo e, ainda, dificultar a manutenção e implementação de novas features. 
Supondo que hajam mais objetos semelhantes que façam funções alheias a ideia principal do objeto, no exemplo, "salvar", ao mudar a forma de salvar os objetos teria que mexer em cada um.

#### Open-Close Principle (OCP) + Specification 

`OpenClosePrincipleAndSpecification.java`

A ideia deste principio é ser "Open" para extensão e "Close" para modificações, ou seja com este principio voce pode utilizar de herança e polimorfismo (extends/implements) para implementar novas funcionalidades, mas deve-se evitar modificações no código já "testado". Desta forma, caso haja a intenção de adicionar funcionalidades, voce utiliza as interfaces para isso e não modificando o código.

Já o design Pattern Specification busca criar classes especificas implementando essas interfaces, classes que tem apenas determinada função.

#### Liskov Substitution Principle (LSP)

`LiskovSubstitutionPrinciple.java`

Este principio diz que uma classe "filha" passada para um método não deve quebrar ou trazer efeitos negativos para a aplicação. 
Sendo assim, se a classe 'V' é filha da classe 'M', então métodos que recebem a classe 'M' podem receber a classe 'V' sem causar efeitos colaterais ao programa.

#### Interface Segregation Principle (ISP)

`InterfaceSegregationPrinciple.java`

Com este principio busca-se dividir interfaces em interfaces menores. Desta forma, evita-se que uma classe que implementa a interface contenha metodos que não são possiveis de implementar, uma vez que, para aquela classe em questão o método não faz sentido. No exemplo desta interface, em vez de criar uma interface que possibilita uma impressora imprimir, scanear e enviar fax, é preferivel que sejam criadas interfaces menores com cada ação, uma vez que poderia ocorrer de uma impressora não enviar fax ou scanear. 

#### Dependency Inversion Principle (DIP)

`DependencyInversionPrinciple.java`

Este principio é diferente de Dependency Injection.
Ele se baseia em duas condições principais:
	1. Módulo "High-level" (alto nivel) não devem depender de módulos "Low-level" (baixo nivel). Ambos devem depender de abstrações.
	2. Abstrações não devem depender de detalhes de implementação. O contrário que deve ocorrer.
	
	Modulos de alto nivel são módulos que dependem de outras classes. 
	Modulos de baixo nivel são módulos que não dependem de outras classes.
	
Desta forma, ao utilizar abstrações o código diminui o acoplamento e, consequentemente facilita substituir implementações sem quebrar o código.

### DesignPatterns

**Design Pattern estruturais**

São Padrões de projetos que se preocupam com a forma que os objetos são compostos para formar estruturas maiores: Adapter, Bridge, Composite, Decorator, Façade, Flyweigth e Proxy.

**Design Pattern criacionais**

São padrões de projetos que se preocupam com a forma que os objetos são criados, visando facilitar a criação, manutenção e garantir a correta criaçaõ do objeto:
Builder, Factory, Prototype e Singleton.  

#### Builder

Este é um design pattern **criacional**, tem como objetivo disponibilizar uma forma de criar objetos e normalmente é utilizado quando há diversas propriedades que devem ser preenchidas. Sendo assim, em vez de mandar todos os parametros em um construtor, opta-se por utilizar um builder para criar o objeto de forma que facilite a criação, evita o envio de parametros na posição errada e melhora a leitura do código.


. Fluent-Builder
	Ocorre quando voce consegue realizar chamadas em sequencia sem a necessidade de utilizar a variavel a cada chamada. Eg:
	
```java
new StringBuild().append(" isto ").append(" é ").append(" fluent-builder ");
```

#### Factory

Este também é um design pattern **criacional**, nele, busca-se criar objetos mais complexos e facilitar a criação ou ainda definir restrições ao criar um objeto. 
O Factory pode ser utilizado como um "Factory Method", onde o construtor do objeto é privado e existe um método estatico para realizar a criação, normalmente utilizado quando o construtor não é "expressivo" o suficiente, ou seja, ao utilizar este construtor não dá pra saber exatamente o que ocorrerá. Ou, ainda, pode-se criar uma classe separada responsável apenas pela criação do objeto. Normalmente, ao existir um factory pode acontecer do objeto ter o construtor privado (buscando evitar que alguem o utilize de maneira erronea) e, desta forma, a solução é criar o factory como uma inner static class. 

Existe também a opção **Abstract Factory** onde a criação do objeto é delegada para uma subclasse

#### Prototype

Este design pattern se trata de replicar, ou seja, clonar objetos. Com ele voce pode utilizar de objetos já criados para modificar apenas algumas propriedades. 
Existem algumas forma de chegar a este resultado:
- Implementar a interface clonable - **Não indicada**
- Implementar um construtor que receberá o objeto que irá clonar
- Utilizar bibliotecas que usam Serialização ou Reflection para clonar, por exemplo, da apache commons: `SerializationUtils.clone()`
	
Ao realizar o clone de um objeto, deve-se tomar cuidado se é um clone "swallow" ou "deepClone". A primeira, trata-se de um clone superficial, onde as outras informações do objeto acabam não sendo clonados e uma modificação nessas classes pode acabar afetando todas as instancias. 
	Já o `DeepClone`, o que normalmente se busca, realiza o clone de toda a arvore deste objeto.

#### Singleton

Este pattern é utilizado para criar uma unica instancia da classe no projeto. Normalmente quando carrega uma informação que não faz sentido ser carregada diversas vezes, ou não haverá modificações após realizado o carregamento, ou, ainda, realizado o carregamento, a informação deva ser centralizada para que qualquer objeto pegue o mesmo valor quando necessitar utiliza-la. 

É importante tomar cuidado ao criar um Singleton com a utilização de carregamento "Lazy" e "Thread Safe". 

##### Problemas com `Basic Singleton`

Dependendo de como o Singleton é implementado, existem algumas formas de "quebrar" este pattern, uma dela é atraves do uso de **reflection** outra por **serialization**. Nestes casos, por mais que a classe singleton não possa ser instanciado novamente, utilizando as formas mencionadas, será criada uma nova instancia da classe. Isso pode acontecer propositalmente, ou por acidente. 

No caso da serialização, pode-se implementar o metodo "readResolve" e obrigar o retorno da instancia estática que já existe, porém é mais um controle que o programador deve estar ciente de realizar.

##### 1) Laziness e Threadsafe

A criação de um singleton utilizando o conceito "lazy", é quando voce apenas cria a instancia quando realmente irá utilizar o singleton. Desta forma, ao utilizar o `getInstance()`, é validado se já existe uma instancia e caso não tenha, o singleton é criado. 

```java
private static LazySingleton instance;

public LazySingleton getInstance(){
if(instance == null){
instance = new LazySingleton();
}
return instance;
}
```

O problema do código acima ocorre quando há diversas threads utilizando o mesmo singleton. Isso pode gerar uma concorrencia no método e criar mais de uma instance do que deveria ser um singleton. Uma forma de corrigir este problema é utilizar o chamado **double-checked locking".


Outra forma de evitar o problema de thread-safe com a inicialização lazy é utilizando InnerClass. No java, uma classe só é inicializada quando é utilizada. Sendo assim, a instancia só será criada quando realmente for chamada e sem a necessidade de sincronização de blocos como no double-checked.

Exemplo: `InnerClassSingleton.java`

##### 2) Singleton utilizando enum

Outra forma de se criar um singleton é utilizando enum. Neste caso, não há problemas com lazyness ou thread-safe, porém, caso utilize Serialização os valores dos campos da enum pode ser perdidos. Isso acontece, pois as Enums são serializaveis, mas apenas o nome. Suas propriedades são consideradas "transient". Caso a aplicação não tenha sido finalizada, ao ler o arquivo novamente, os valores da enum estarão corretos, entretando, após a finalização do processo, reinicialização e releitura do arquivo, o valor da enum voltará para o valor inicial do construtor. 

De modo geral, a utilização de enum como singleton tem a proteção no caso de lazyness e thread-safe, mas caso seja necessário serializar e deserializar os valores da enum serão perdidos.

https://stackoverflow.com/questions/15521309/is-custom-enum-serializable-too/15522276#15522276

##### 3) Monostate

É possivel criar diversas instancias da classe, porém todas as propriedades são estáticas. Não é _aconselhavel utilizar desta forma_, mas pode acontecer quando já existem diversos locais na aplicação criando novas instancias e posterioremente muda-se para uma forma de singleton, onde todos veem e modificam os mesmos valores. 

##### 4) Multiton

Não é basicamente um singleton, mas permite a criação definida de um objeto baseado em chave <=> valor. Após criada a instancia para determinada chave, não se cria novamente, retorna a instancia previamente criada.  

##### 5) Testability

Um dos problemas com a utilização do Design Pattern Singleton é a realização de testes unitários. Uma vez que a utilização é realizada diretamente na classe, sem a criação da instancia pela classe dependente, ao realizar o teste desta segunda classe, acaba-se testando o singleton também, quando não era a intenção, o que não é mais um teste unitário, e sim integrado. 
Uma forma de corrigir este problema é a utilização de **Injeção de Dependencia** e abstração. Em vez de se utilizar a classe concreta, utiliza-se a abstrata e o valor é injetado. Desta forma, pode-se utilizar tanto um singleton que carrega de um arquivo, de um banco de dados, fixo no texto (para teste), enfim. 

#### Adapter

_Categoria: Estrutural_

Como o próprio nome sugere, este design pattern permite adaptar uma classe/interface a outra. Desta forma, uma implementação pre-existente pode ser reaproveitada. 
Com o adapter voce abstrai todas as complexidades de conversão de um objeto para outro e permite, por exemplo, que novas bibliotecas se comuniquem com antigas sem modificar o código, apenas realizando as adaptações para que elas passem a se comunicar.

#### Bridge

_Categoria: Estrutural_

Este padrão de projeto visa facilitar a implementação de classes abstratas e interfaces evitando o crescimento exponencial de classes. Por exemplo: Supondo que exista uma classe `Forma` e 2 subclasses `Circulo`e `Quadrado`. Voce quer estender essa hierarquia de forma a existirem cores, então são criadas as subclasses `Vermelho` e `Azul`. Porém, como já existem 2 subclasses `Circulo`e `Quadrado`, agora seriam necessárias 4 classes como: `CirculoAzul`, `CirculoVermelho`, etc.
Adicionando novos tipos ou cores faria essa hierarquia crescer exponencialmente, por exemplo, adicionar um `Triangulo`, precisaria de 2 classes novas e, depois, mais uma cor, seriam mais 3 classes, e assim por diante. 

Este problema ocorre, pois estamos tentando estender a forma em 2 dimensões independentes: Cor e Forma. 

Com este padrão, tenta-se resolver esse problema modificando de `Herança`para composição. Agora, em vez de herdar as cores, a forma *possui* uma cor. Então, todo o código relacionado a cor é removido da classe `Circulo`, por exemplo, e colocado em uma classe própria da cor (Ex: `Azul`) e sua utilização é delegada para esta outra classe. 

**Antes** 

![Bridge Before](/doc-images/bridge_before.png)

**Depois**

![Bridge After](/doc-images/bridge_after.png)

#### Composite
 
_Categoria: Estrutural_

Este padrão é um padrão estrutural que facilita na criação, principalmente, de árvores e hierarquias. Com ele, voce monta a estrutura das classes de forma a facilitar realizar a manutenção, navegar entre as classes, utilizar suas funções, etc. 
Permite tratar tanto objetos individuais quanto grupos de objetos de maneira mais simples

![Composite](/doc-images/composite.png)

#### Decorator

_Categoria: Estrutural_

Este design pattern permite adicionar novas funcionalidades a uma classe já existente atraves de agregação. Desta forma, evita-se quebrar o principio OCP e é utilizado também muitas vezes quando a classe é final, e não se pode utilizar herança.

#### Façade

_Categoria: Estrutural_

Este design pattern se assemelha com o pattern **factory**, entretanto, enquanto o factory cria um objeto, este procura criar toda uma estrutura para facilitar a utilização de apenas uma API, seja por operações ou criação de objetos, por exemplo:
Para escrever em um console, deve-se criar um buffer, adicionar a viewport, definir o tamanho da viewport, definir qual o tamanho do buffer, etc, porém, para o usuário final, ele apenas quer escrever no console. Todas as outras informações não são relevantes para ele e este design pattern busca abstrair toda essa construção e utilização disponibilizando métodos simples para consumo. 

Em resumo, este padrão busca disponibilizar uma API simplificada a respeito da utilização de diversas classes

#### Flyweight

Este design pattern busca uma optimização de espaço, desta forma, evita-se redundancia, por exemplo, de Strings, imagens, etc. Assim, pode-se quebrar as strings em pedaços e utilizar uma forma de "apontar", talvez por indices, para esses itens repetidos, em vez de alocar espaço para cada um deles. Ou seja, esta tecnica busca utilizar menos memoria armazenando dados repetidos "externamente" e agrupando objetos similares.

#### Proxy

Este design pattern funciona como um "proxy" comum que já conhecemos na computação. É como se o objeto destino fosse "interceptado" ou passasse por um outro objeto antes de chegar ao destino final. Podemos utilizar este proxy como validações que devem ser feitas antes ou acrescentar funções no método que se está chamando, como por exemplo "logging", "caching", etc... O proxy normalmente tem a mesma API do objeto destino.

##### Decorator vs Proxy
A diferença entre estes dois padrões é que o decorator adiciona novas funcionalidades (métodos) a um objeto/api, enquanto o proxy mantém a mesma API, porém modifica ou adiciona funcionalidades na própria chamada da API ou mascara o que realmente está acontecendo, as vezes nem utilizando um objeto real.

Ex: 
**Decorator**

```java

Person {
  public void getName(){/*code*/}
}

PersonDecorator {
  public void getName(){ person.getName();}
	
  //adicionando funcionalidade
  public void getJob(){//code}
}
``` 

**Proxy**
```java
Person {
  public String getName(){/*code*/}
}

PersonProxy {
public String getName(){
  //realizando novas funções
  log(person);
  if(person.isAnonymous(){
    return "hidden";
  }
  return person.getName();
}


``` 

#### Chain of responsability

_Categoria: Comportamental_

Permite encadear chamadas para que varios objetos possam tratar as requisições (no caso, a requisição pode ser qualquer coisa, desde uma chamada http, ou simplesmente a utilização de um objeto). Assim, existe um evento em cadeia onde cada parte tem sua responsabilidade no tratamento da requisição. 

#### Command

_Categoria: Comportamental_

Este design pattern visa desacoplar a aplicação criando um objeto para tratar cada pedido enviado, encapsulando todos os detalhes da operação que será realizada nesse novo objeto. Este tipo padrão também facilita a criação de filas ou execuções de comando em sequencia, agendar, fazer e desfazer operações, etc. 

#### Interpreter

_Categoria: Comportamental_

Este design pattern visa "traduzir" expressões especificas para uma linguagem de programação, ou, no caso do Java, para uma estrutura de Orientação de Objetos.

#### Iterator

_Categoria: Comportamental_

Este design pattern refere-se a estrutura de dados, pilha, fila, grafos, arvores, etc. Com este padrão, é possivel iterar os dados de uma estrutura desacoplando a implementação da iteração do objeto em si, delegando a iteração para outros objetos.

#### Mediator

_Categoria: Comportamental_

Este padrão de projeto busca implementar um "mediador" entre os objetos. Normalmente é utilizado em "chat rooms", "Players MMORPG", etc, quando um "componente" pode entrar e sair do sistema. Desta forma, o mediador é o responsável por enviar mensagens, por exemplo e controlar os fluxos de acordo. No exemplo do chat, cada pessoa não precisa ter uma referencia a outra pessoa, mas apenas a sala na qual ela está, e, ao enviar a mensagem, o mediador será responsável por entregar a mensagem para os destinatarios de acordo.

Em resumo, este pattern facilita a comunicação entre componentes sem a necessidade que esses componentes estejam cientes um do outro, o mediador realiza a comunicação entre eles.


_Diferença entre Mediator e Observe: https://stackoverflow.com/questions/9226479/mediator-vs-observer-object-oriented-design-patterns_

#### Memento

_Categoria: Comportamental_

De maneira geral, funciona como criação de backups de objetos. Sem violar os encapsulamentos do objeto, ao realizar alguma modificação numa determinada classe, são criados mementos que permitirão realizar o restore do objeto caso necessário.

#### Null Object

_Categoria: Comportamental_

Este design pattern por mais que seja do tipo comportamental, ele não possui nenhum comportamento. A ideia deste padrão é evitar que exceções ocorram, sendo assim, ao existir uma relação de dependencia entre objetos, caso seja necessário que a dependencia não tenha comportamentos, em vez de se enviar "null", utiliza-se este padrão, onde é enviado um objeto, porém ele não possui comportamento algum, apenas evita que problemas ocorram com o código.

#### Observe

_Categoria: Comportamental_

Observe indica que um objeto deseja ser notificado quando determinado campo, ou evento ocorra no sistema. Assim, as entidades que geram esses eventos são chamadas de "observables" e suas ações notificarão quem as observa.  

#### State

_Categoria: Comportamental_

Este padrão de projeto visa desacoplar os estados da aplicação do seu contexto. Com isto, evita-se a criação de diversas condições de acordo com o estado do Objeto e delega o funcionamento do contexto para o objeto de acordo com seu estado e permissoes que aquele estado possui. 
Por exemplo, imagine que há um pedido de ecommerce e que para enviar o pedido para o cliente, o estado do pedido deva ser "Pago", ao solicitar que se envie o pedido deve-se verificar qual o estado atual do pedido e se aquele estado permite ou nao o envio. Com este padrão o próprio estado é responsável por validar o que ele pode ou não fazer. 


#### Strategy

_Categoria: Comportamental_

Possibilita a implementação de estratégias separadamente do contexto. Com este padrão, busca-se separar os algoritmos responsáveis por determinadas ações e sua utilização ocorre normalmente quando existem variações de algoritmos aplicados a determinadas ações e podem ser alterados em tempo de execução. Por exemplo: Imagine que voce esteja criando formas de renderizar determinado conteudo. De acordo com a seleção do usuário, voce renderiza esse conteudo em HTML ou MARKDOWN. Voce pode optar por criar estratégias para separar o algoritmo de criação dessa renderização para cada tipo.

#### Template Method

_Categoria: Comportamental_

De maneira similar ao Design **Strategy**, este design pattern busca modificar implementações de algoritmos, porém, nele, voce modifica apenas partes da implementação. Sendo assim, enquanto no Strategy trabalha-se com interfaces e cada estratégia implementa um algoritmo completo de acordo com aquela estratégia, no Template existe uma "base" já realizada, normalmente usando herança, a subclasse implementa as partes restantes para completar o algoritmo desejado. 

#### Visitor

_Categoria: Comportamental_

Este design pattern visa facilitar a utilização de um algoritmo quando se tem um estrutura complexa e deseja-se separar uma lógica complexa. Com ele, é possivel visitar os elementos da estrutura e realizar determinada operação de maneira mais simples. Além disso, buscando cumprir o principio de OCP, ele permite que sejam aplicados novos algoritmos ou modificações sem mudanças na estrutura já montada. Normalmente, utiliza-se a forma **Double Dispatch** deste pattern.

## Materiais uteis

- SOLID (Deschamps): https://www.youtube.com/watch?v=6SfrO3D4dHM 
	- LSP: https://www.youtube.com/watch?v=XbLDhq3fk5o 
	- LSP: https://www.youtube.com/watch?v=Mmy1EUKC_iE
	- DIP: https://www.youtube.com/watch?v=DQA6BIwslwY
	- DIP: https://www.youtube.com/watch?v=clpc72MS0YE
- Design Patterns: 
	- https://www.youtube.com/watch?v=NU_1StN5Tkk
	- https://www.youtube.com/playlist?list=PLbIBj8vQhvm0VY5YrMrafWaQY2EnJ3j8H
	- https://sourcemaking.com/design_patterns/command
	- https://refactoring.guru/pt-br/design-patterns/
	- Adapter: https://www.youtube.com/watch?reload=9&v=qG286LQM6BU
	- Bridge: https://www.youtube.com/watch?v=9jIgSsIfh_8
	- Bridge: https://www.youtube.com/watch?v=-gsuMWLxAko	
	- Composite: https://www.youtube.com/watch?v=2HUnoKyC9l0
	- Composite: https://www.youtube.com/watch?v=f4jLEVxP1_U
	- Interpreter: https://www.baeldung.com/java-interpreter-pattern