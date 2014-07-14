Music-Clustering
================

The goal of this project is to develop algorithms to perform automatic
playlist generation via music clustering.
Given all the music on a computer we cluster the songs/artists
into k playlists such that the music in each playlist is similar.
To do so, we use data mining techniques and information about artists
from a downloaded dataset.
We use a dataset from the social music website Last.fm : 
The dataset used is a Many-to-many relational database between artists
and tags (genres) imported in a MySQL database.

![Alt tag](https://raw.githubusercontent.com/guillaumedupre/Music-Clustering/master/img/dataset.png "Screenshot of a few rows from the dataset")

We implement two algorithms to perform music cluster :

1) Bag-of-words representation + k-means algorithm
--------------------------------------------------
The Bag-of-words representation constists in choosing some tags as a
basis for all the artists.
We then use the k-means algorithm to cluster the artists.

2) Weighted Jacquard distance + k-medoids algorithm
---------------------------------------------------

Artists can be seen as buckets of genres ⇒ Jacquard index : http://en.wikipedia.org/wiki/Jaccard_index
The Weighted Jacquard similarity can be computed with a SQL query (WeightedJacquard.java), we obtain a distance matrix for all our artists.
We then use the k-medoids algorithm to cluster the artits.



