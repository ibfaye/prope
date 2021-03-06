ds = read.csv("c:/workspaces/results.csv", sep=";")
active <- subset(ds, group == "active")
ode <- subset(ds, group == "ode")
bpelg <- subset(ds, group == "bpelg")
openesb <- subset(ds, group == "openesb")
orchestra <- subset(ds, group == "orchestra")
petals <- subset(ds, group == "petals")
library(Rcmdr)
numSummary(openesb$DDS,statistics=c("mean","sd","cv"))
numSummary(petals$DDS,statistics=c("mean","sd","cv"))
numSummary(ode$DDS,statistics=c("mean","sd","cv"))
numSummary(bpelg$DDS,statistics=c("mean","sd","cv"))
numSummary(orchestra$DDS,statistics=c("mean","sd","cv"))
numSummary(active$DDS,statistics=c("mean","sd","cv"))

numSummary(openesb$EPC,statistics=c("mean","sd","cv"))
numSummary(petals$EPC,statistics=c("mean","sd","cv"))
numSummary(ode$EPC,statistics=c("mean","sd","cv"))
numSummary(bpelg$EPC,statistics=c("mean","sd","cv"))
numSummary(orchestra$EPC,statistics=c("mean","sd","cv"))
numSummary(active$EPC,statistics=c("mean","sd","cv"))

numSummary(openesb$DE,statistics=c("mean","sd","cv"))
numSummary(petals$DE,statistics=c("mean","sd","cv"))
numSummary(ode$DE,statistics=c("mean","sd","cv"))
numSummary(bpelg$DE,statistics=c("mean","sd","cv"))
numSummary(orchestra$DE,statistics=c("mean","sd","cv"))
numSummary(active$DE,statistics=c("mean","sd","cv"))