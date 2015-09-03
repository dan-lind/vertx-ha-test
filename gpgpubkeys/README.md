# Våra publika gpg-nycklar

Ska vi maila känsliga data till varandra tycker jag vi ska kryptera dessa mail.
För OSX finns [GPGTools!](https://gpgtools.org)

Har man homebrew installerat kan man istället köra:

    brew install Caskroom/cask/gpgtools

Sedan skapar man ett nyckelpar. Enklast är med prorgrammet GPG Keychain som följer med i programsviten ovan. 

När det är gjort, exportera er publika nyckel (slutar på .asc) och pusha in den här under denna katalog. 
Döp filen till exempelvis **<förnamn>.asc**

## Använda GPG Tools från terminalen

##### Lista publika nycklar

    gpg --list-keys
    
##### Lista privata nycklar    

    gpg --list-secret-keys
    
##### Kryptera en fil

     gpg --output filnamn.txt.gpg --encrypt --recipient namn@omegapoint.se filnamn.txt
    
##### Dekryptera en fil

    gpg -d filnamn.txt.gpg


## pygpg

Ett ytterligare alternativ för att kryptera och dekryptera är att använda det lilla program jag gjorde första veckan som Omegapointare då jag satt på bänken, nämligen [pygpg!](https://github.com/maxhope/pygpg). 