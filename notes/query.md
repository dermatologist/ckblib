match (d {cui: 'C0014518'}) return d

match (d {cui: 'C0014518'}) -[r]- (s) return d, r, s

match (d {cui: 'C0014518'}) -[r]- (s) match (s) -- (t) return d, r, s, t

MATCH (d {cui: 'C0014518'}) -[r]- (s) RETURN d.name AS concept, collect(s.name) AS features, collect(r.confidence) AS value


MATCH (d {cui: 'C0014518'}) -[r]- (s)
MATCH (s) -[r2]- (s2)
RETURN d.name AS concept, collect([s.name, s2.name]) AS features, collect([r.confidence, r2.confidence]) AS value


return d, r, s, t