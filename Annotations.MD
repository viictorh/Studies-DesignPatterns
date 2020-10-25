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

## Materiais uteis

- SOLID (Deschamps): https://www.youtube.com/watch?v=6SfrO3D4dHM 
- LSP: https://www.youtube.com/watch?v=XbLDhq3fk5o 
- LSP: https://www.youtube.com/watch?v=Mmy1EUKC_iE
- DIP: https://www.youtube.com/watch?v=DQA6BIwslwY
- DIP: https://www.youtube.com/watch?v=clpc72MS0YE