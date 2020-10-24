# Anotações


### DesignPatterns

#### Single Responsible Principle (SRP)

`SingleResponsiblePrinciple.java`
 
Indica que uma classe deve ter apenas uma responsabilidade. Caso isso não ocorra, pode gerar um "GOD Object", ou seja, uma classe que faz tudo e, ainda, dificultar a manutenção e implementação de novas features. 
Supondo que hajam mais objetos semelhantes que façam funções alheias a ideia principal do objeto, no exemplo, "salvar", ao mudar a forma de salvar os objetos teria que mexer em cada um.

#### Open-Close Principle (OCP) + Specification 

`OpenClosePrincipleAndSpecification.java`

A ideia deste principio é ser "Open" para extensão e "Close" para modificações, ou seja com este principio voce pode utilizar de herança e polimorfismo (extends/implements) para implementar novas funcionalidades, mas deve-se evitar modificações no código já "testado". Desta forma, caso haja a intenção de adicionar funcionalidades, voce utiliza as interfaces para isso e não modificando o código.

Já o design Pattern Specification busca criar classes especificas implementando essas interfaces, classes que tem apenas determinada função.