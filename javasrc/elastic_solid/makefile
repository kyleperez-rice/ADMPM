EXEC = MPMSolve
EXEC_FILE = $(EXEC).java

DEPS0 = Accelerations.java
DEPS1 = BoundaryConditions.java
DEPS2 = DataWrite.java
DEPS3 = Debug.java
DEPS4 = Initializations.java
DEPS5 = InitialState.java
DEPS6 = MaterialPoint.java
DEPS7 = Node.java
DEPS8 = MPMMath.java
DEPS9 = SimUpdate.java
DEPS10 = Constants.java



FILES = $(EXEC_FILE) $(DEPS0) $(DEPS1) $(DEPS2) $(DEPS3) $(DEPS4) $(DEPS5) $(DEPS6) $(DEPS7) $(DEPS8) $(DEPS9) $(DEPS10)


$(EXEC): $(FILES)
	javac \
	$(FILES)

clean:
	rm *.class
