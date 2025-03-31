Klasicni pristup koristeci CLEAN, MVVM. Nisu korisceni UseCase posto konkretno za zadatak ne bi bili od prevelike koristi, 
vec je entry point za domain layer repository. Akcenat resenja je na dobroj inicialnoj postavci, arhitekturi koja bi bila dobar 
pocetak za dalji razvoj projekta. Nisu pokriveni svi moguci case-vi, error statovi, data order itd koji bi bili da se radi produkcioni app.

Pretpostavka da su GitRepository i GitRepositoryDetails dva razlicita entiteta.

Features:

REPOSITORY LIST: Cuvanje podataka u lokalu, ekran kombinuje tri izvora podataka.

========= 1) status Fetch operacije (loading, success, error), skida podatke sa api-a i snima ih u bazu

========= 2) stream iz baze podataka. U bazi se kesiraju repositoriji, rezultujuci stream je "RepositoryCache INNER JOIN RepositoryListIndex LEFT JOIN FavouriteRepoIndex"

========= 3) search query. U produkciji search bi najbolje bilo da bude implementiran kao poseban ekran, zbog api-a, paginacije itd. Ovo je pokazni zadatak pa sam ga odradio u istom ekranu. Dalje moze da se razradi sa dva state-a SEARCH i REGULAR pa bi u tom slucaju search mogao da radi paginaciju i skida nove podatke sa neta itd.


REPOSITORY DETAILS: Dva strima podataka.

========= 1) REPO DETAILS kombinuje Fetch operaciju (loading, success, error) koja skida details sa neta i snima u bazu, drugi koji observuje "RepoDetailsCacheById LEFT JOIN FavouriteRepoIndex"

========= 2) CONTRIBUTORS kombinuje Fetch operaciju (loading, success, error) koja skida kontributore sa neta i snima ih u bazu, i drugi koji observuje "ContributorsCache INNER JOIN RepositoryCacheByIdContributors LEFT JOIN FavouriteContributorsIndex".


FAVOURITE: Informacije i favouritovanim repozitorijima i contributorima se cuvaju lokalno u posebnoj tabeli u bazi koja cuva ID-eve. Spajanje podataka koje vraca api i lokalno cuvanih kao favourite status se moze izvrsiti na vise mesta. Npr da se slusa u presentation layeru sam podatak posebno od favorite statusa i tu se merguju. Ja sam se odlucio da se merguju u DATA layeru, posto je prirodno da je 
favourite status deo samog podatka koji dolazi sa bekenda. Svaki nacin ima svoje za i protiv. 


FAVOURITE LIST: Observovanje kesiranih podataka "Cache LEFT JOIN FavouriteIndex".

Zbog ogranicenog vremena i drugih obaveza, ostalo je dosta prostora za dalju razradu zadatka, 
uglavnom fizikalija koje bi se sada nadogradjivale na osnovu, npr pokrivanje ostalih caseva, 
dalje ulepsavanje i razrada UI-a koji je inicialno izradjen samo za cilj pokrivanja funkcionalnosti aplikacije
kao i testova, dodacu primere naknadno.


