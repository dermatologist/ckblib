branch=$(git rev-parse --abbrev-ref HEAD | xargs basename) # Takes version number from release/version
module load maven
mvn versions:set -DnewVersion=$branch -DgenerateBackupPoms=false -DprocessAllModules --file pom-deploy.xml
mvn versions:set -DnewVersion=$branch -DgenerateBackupPoms=false -DprocessAllModules