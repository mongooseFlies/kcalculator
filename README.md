# Kotlin Calculator

- Calculator that support basic mathematical operations while respecting association and precedence Using Kotlin programming language.
- Precision = 4 digits after decimal point (For example: 1/3 = 0.3333).

***
## TODO
- [x] supports basic operations - + * /
- [x] supports brackets
- [x] respects operations priority
- [x] 100% test coverage using Spek
- [x] used AST data structure as an IR with minimal burden and details
- [ ] add sin, cos, unary and other math functions

## Description

- The Grammar used to process the input is the following:

| METHOD | ->  | BODY                 |
|--------|:---:|:---------------------|
| EXPR   | ->  | TERM +- TERM         |
| TERM   | ->  | FACTOR */ FACTOR     |
| FACTOR | ->  | NUMBER<br/> ( EXPR ) |

- Lexer class produces Tokens from input String 
- Parser class produces AST from Tokens produced by Lexer
- Processor class traverse the AST produced by the parser and gives the final result rounded by 4 digits max

