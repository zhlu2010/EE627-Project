Team members: Hanyi Zhang, Kunpeng Hao, Zhonghao Lu

I. Results
1.	First approach, weights
(1). {album : 0.25; singer and genre : 0.75} accuracy = 0.83116
(2). {album : 0.3; singer and genre : 0.7} accuracy = 0.83352
(3). {album : 0.35; singer and genre : 0.65} accuracy = 0.83619
(4). {album : 0.45; singer and genre : 0.55} accuracy = 0.83961
(5). {album : 0.6; singer and genre : 0.4} accuracy = 0.84461
(6). {album : 0.7; singer and genre : 0.3} accuracy = 0.84536

2.  Second approach, rank
  (1) rank = 10, accuracy = 0.67855
  (2) rank = 15, accuracy = 0.66430
(3) rank = 20, accuracy = 0.65858
(4) rank = 30, accuracy = 0.65636

3.  ensemble
  (1). Use results from weights(1,2,3,4,5) and rank(1,2,3,4) accuracy = 0.84461
  (2). Use all the results, accuracy = 0.84536

II. Algorithm
1.	predict scores by users own data: 
(1). Find relationship between items and singer, album, genres 
(2). Calculate scores for test data according to the scores which have same singer, album or genres from the training data 
(3). Choose top three scores and set them to be recommended
  2.  predict scores by other users¡¯data
     (1). Matrix factorization for training data
     (2). Predict test scores
3.  ensemble all the results
(1). Put previous results together as a matrix
(2). Compute weights for each result by the accuracy rate and matrix
(3). Predict scores
III. conclusion
(1). The final accuracy score is similar with the highest score in ensemble matrix.
(2). Only first approach can get a better score by tuning weights, maybe we need more methods such as(SVM) to resolve this problem or more data features.




