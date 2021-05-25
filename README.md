# Анализатор графов
 Это приложение позволяет выделять сообщества в графах, находить ключевые вершины и делать раскладку. Оно поддерживает чтение и запись в `.csv` и `.db` (SQLite).


## Установка и запуск
 Приложение работает на Java SDK 15 версии. 

- Установка:

```
git clone https://github.com/spbu-team-11/graph-analyzer-app
```

- Запуск:

#### Windows

```
.\gradlew.bat run
```

#### Linux

```
./gradlew run
```

#### MacOS

```
./gradlew run
```


## Использование [релиза](https://github.com/spbu-team-11/graph-analyzer-app/releases)
#### JAR

 Приложение работает на Java SDK 15 версии. 

```
java -jar graph-analyzer-app.jar
```


## Выделение сообществ 
 Для выделения сообществ используется [Leiden alghoritm](https://www.nature.com/articles/s41598-019-41695-z), в качестве параметров ему передаются:

 1. Количество итераций алгоритма. (Iteration)
 2. Параметр отвечающий за размер выделяемых сообществ, более высокое значение ведет к большему количеству сообществ, а более низкое разрешение ведет к меньшему количеству сообществ. (Resolution)

Сообщества графически выделяются цветом, также можно посмотреть числовую метку сообщества (Labels &#8594; Community)


## Выделение ключевых вершин 
 Для выделения ключевых вершин используется [Harmonic centrality](http://infoscience.epfl.ch/record/200525/files/%5bEN%5dASNA09.pdf)
с алгоритмом Дейкстры и нормализованновым показателем centrality. Вершины выделяется размером, который считается по формуле: 

![Formula](https://render.githubusercontent.com/render/math?math=R*\frac{2((e+k)^x-(e+\frac{k}{2})^x)}{k}&mode=inline)

 - _**`x`**_ - показатель centrality
 - _**`R`**_ - стандартный размер вершины
 - _**`k`**_ - передаваемый параметр (Size-Ratio coefficient)

> Чем больше _**`k`**_, тем больше отношение размеров вершин с разным значением centrality.


## Раскладка графа
 Для раскладки графа используется алгоритм [ForceAtlas2](https://journals.plos.org/plosone/article?id=10.1371/journal.pone.0098679). В качестве параметров ему передаются:


 - Количество итераций алгоритма. (Iteration)
 - Сила притяжения вершин друг к другу. (Gravity)
 - Активация/деактивация расчета с помощью логарифмической силы притяжения. (Logarithmic attraction mode) 
 - Активация/деактивация притяжения вершин к краям окна. (Outbound attraction mode)
 - Активация/деактивация притяжения вершин к центру. (Strong gravity mode)          


## SQLite
 Приложение имеет возможность сохранять и читать 2 типа SQLite баз данных (сохранение идет во 2 тип)
 
 1) Необработанный граф. В таком случае в базе данных должно быть две таблицы
 
 
### _**Vertices**_

|  id | element | community |
|--|--|--|
| *integer* | *text* | -1 |

### _**Edges**_

|  id | element | first | second |
|--|--|--|--|
| *integer* | *text* | *int* | *int* |

 - _**`id`**_ - идентификационный номер _(должен быть уникальным)_ 

 - _**`first`**_ - откуда идет ребро _(**id** вершины из первой таблицы)_

 - _**`second`**_ - куда идет ребро _(**id** вершины из первой таблицы)_

2. Обработанный граф. В таком случае должна быть третья таблица, а в таблице **Vertices** в поле **community** вместо -1 могут стоять непосредственно номера community

### _**VerticesView**_

|  id| vertex | x | y| color |
|--|--|--|--|--|
| *integer* | *int* | *double*| *double*| *text*|

 - _**`id`**_ должен быть уникален

 - _**`vertex`**_ - **id** вершины из таблицы **Vertices**

 - _**`x, y`**_ - координаты вершины

 - _**`color`**_ - цвет формата RGB в следующем виде "r/g/b",  где r,g,b - double.


## CSV-файлы
 Приложение так же имеет возможность сохранять графы в файлы формата `.csv`, а также считывать их.

> Чтобы приложение могло считать граф, csv-файл должен соответствовать стандарту описанному ниже.

Заголовок:

```
isNode,name,x,y,color,radius,community,from,to
```
      
Остальные строчки должны представлять из себя заполненные (где это нужно) поля заголовка, разделенные запятой:

 - _**`isNode`**_ - значение true/false _(вершина либо ребро)_
 - _**`name`**_ - имя вершины/ребра _(уникальное поле)_
 - _**`x`**_, _**`y`**_ - координаты вершины _(необязательное поле в случае вершины, в случае ребра - пустое)_
 - _**`color`**_ - цвет вершины в формате "r/g/b", где "r", "g", "b" - числа из rgb представления цвета, а "/" - разделитель _(необязательное поле в случае вершины, в случае ребра - пустое)_
 - _**`radius`**_ - числовое представление радиуса вершины _(необязательное поле в случае вершины, в случае ребра - пустое)_
 - _**`community`**_ - номер сообщества вершины _(необязательное поле в случае вершины, в случае ребра - пустое)_
 - _**`from`**_, _**`to`**_ - имена вершин, которые соответствуют началу и концу ребра _(пустое для вершин поле)_
