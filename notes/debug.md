2021-04-18 11:38:41.549  WARN 36071 --- [o4jDriverIO-5-2] org.springframework.data.neo4j.cypher    : Neo.ClientNotification.Statement.CartesianProductWarning: This query builds a cartesian product between disconnected patterns.
        MATCH (d:Disease {cui: $dcui}) MATCH (s:Anatomy {cui: $scui})
        ^
        MERGE (d) <-[r:ANATOMY_OF]- (s)
        ON CREATE SET r.confidence = 1, r.upvote = 0, r.downvote = 0
        ON MATCH SET r.confidence = r.confidence +  $c, r.upvote = r.upvote +  $u, r.downvote = r.downvote +  $d
        RETURN DISTINCT s
If a part of a query contains multiple disconnected patterns, this will build a cartesian product between all those parts. This may produce a large amount of data and slow down query processing. While occasionally intended, it may often be possible to reformulate the query that avoids the use of this cross product, perhaps by adding a relationship between the different parts or by using OPTIONAL MATCH (identifier is: (s))