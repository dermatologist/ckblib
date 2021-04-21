match (d {cui: 'C0014518'}) return d

match (d {cui: 'C0014518'}) -[r]- (s) return d, r, s

match (d {cui: 'C0014518'}) -[r]- (s) match (s) -- (t) return d, r, s, t

MATCH (d {cui: 'C0014518'}) -[r]- (s) RETURN d.name AS concept, collect(s.name) AS features, collect(r.confidence) AS value


MATCH (d {cui: 'C0014518'}) -[r]- (s)
MATCH (s) -[r2]- (s2)
RETURN d.name AS concept, collect([s.name, s2.name]) AS features, collect([r.confidence, r2.confidence]) AS value


return d, r, s, t


[{"queryKeys":{"keys":["d","r","s"],"keyIndex":{"d":0,"r":1,"s":2}},"values":[{"adapted":{"labels":["Disease"],"id":0,"properties":{"name":{"val":"Psoriasis Vulgaris"},"version":{"val":0},"cui":{"val":"C0041834"}}}},{"adapted":{"start":2,"end":0,"type":"PRESENTATION_OF","id":1,"properties":{"downvote":{"val":0},"upvote":{"val":0},"confidence":{"val":1}}}},{"adapted":{"labels":["Symptom"],"id":2,"properties":{"name":{"val":"Erythema"},"version":{"val":1},"cui":{"val":"C1041835"}}}}],"hashCode":0},{"queryKeys":{"keys":["d","r","s"],"keyIndex":{"d":0,"r":1,"s":2}},"values":[{"adapted":{"labels":["Disease"],"id":0,"properties":{"name":{"val":"Psoriasis Vulgaris"},"version":{"val":0},"cui":{"val":"C0041834"}}}},{"adapted":{"start":1,"end":0,"type":"PRESENTATION_OF","id":0,"properties":{"downvote":{"val":0},"upvote":{"val":0},"confidence":{"val":1}}}},{"adapted":{"labels":["Symptom"],"id":1,"properties":{"name":{"val":"Pruritus"},"version":{"val":0},"cui":{"val":"C1041834"}}}}],"hashCode":0}]