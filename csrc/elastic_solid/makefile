EXEC = mpm_solve
EXEC_FILE = $(EXEC).c

DEPS = solver_options.c 


FILES = $(EXEC_FILE) $(DEPS)

#INCLUDES = /home/kyleperez/cppstuff/cpplibraries/eigen-3.3.8/
#-I $(INCLUDES)

# -g

$(EXEC): $(FILES)
	gcc \
	-o $(EXEC) \
	$(FILES) \
	-lm
	
clean:
	rm $(EXEC)
