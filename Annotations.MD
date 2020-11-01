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

##### Laziness e Threadsafe

A criação de um singleton utilizando o conceito "lazy", é quando voce apenas cria a instancia quando realmente irá utilizar o singleton. Desta forma, ao utilizar o `getInstance()`, é validado se já existe uma instancia e caso não tenha, o singleton é criado. 

```java
private static LazySingleton instance;

public LazySingleton getInstance(){
	if(instance == null){
		instance = new LazySingleton();
	}
	return instance;
}

O problema do código acima ocorre quando há diversas threads utilizando o mesmo singleton. Isso pode gerar uma concorrencia no método e criar mais de uma instance do que deveria ser um singleton. Uma forma de corrigir este problema é utilizar o chamado **double-checked locking".

```

Outra forma de evitar o problema de thread-safe com a inicialização lazy é utilizando InnerClass. No java, uma classe só é inicializada quando é utilizada. Sendo assim, a instancia só será criada quando realmente for chamada e sem a necessidade de sincronização de blocos como no double-checked.

Exemplo: `InnerClassSingleton.java`

##### Singleton utilizando enum

Outra forma de se criar um singleton é utilizando enum. Neste caso, não há problemas com lazyness ou thread-safe, porém, caso utilize Serialização os valores dos campos da enum pode ser perdidos. Isso acontece, pois as Enums são serializaveis, mas apenas o nome. Suas propriedades são consideradas "transient". Caso a aplicação não tenha sido finalizada, ao ler o arquivo novamente, os valores da enum estarão corretos, entretando, após a finalização do processo, reinicialização e releitura do arquivo, o valor da enum voltará para o valor inicial do construtor. 

De modo geral, a utilização de enum como singleton tem a proteção no caso de lazyness e thread-safe, mas caso seja necessário serializar e deserializar os valores da enum serão perdidos.

https://stackoverflow.com/questions/15521309/is-custom-enum-serializable-too/15522276#15522276

##### Monostate

É possivel criar diversas instancias da classe, porém todas as propriedades são estáticas. Não é _aconselhavel utilizar desta forma_, mas pode acontecer quando já existem diversos locais na aplicação criando novas instancias e posterioremente muda-se para uma forma de singleton, onde todos veem e modificam os mesmos valores. 

##### Multiton

Não é basicamente um singleton, mas permite a criação definida de um objeto baseado em chave <=> valor. Após criada a instancia para determinada chave, não se cria novamente, retorna a instancia previamente criada.  

## Materiais uteis

- SOLID (Deschamps): https://www.youtube.com/watch?v=6SfrO3D4dHM 
	- LSP: https://www.youtube.com/watch?v=XbLDhq3fk5o 
	- LSP: https://www.youtube.com/watch?v=Mmy1EUKC_iE
	- DIP: https://www.youtube.com/watch?v=DQA6BIwslwY
	- DIP: https://www.youtube.com/watch?v=clpc72MS0YE
- Design Patterns: https://www.youtube.com/watch?v=NU_1StN5Tkk