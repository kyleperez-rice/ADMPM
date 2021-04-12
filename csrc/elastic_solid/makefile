EXEC = mpm_solve
EXEC_FILE = $(EXEC).c

DEPS1 = mpm_math.c
DEPS2 = initial_state.c
DEPS3 = node.c
DEPS5 = accelerations.c
DEPS7 = initializations.c
DEPS8 = data_write.c
DEPS9 = sim_update.c
DEPS10 = solver_options.c
DEPS11 = boundary_conditions.c

FILES = $(EXEC_FILE) $(DEPS1) $(DEPS2) $(DEPS3) $(DEPS5) $(DEPS7) $(DEPS8) $(DEPS9) $(DEPS10) $(DEPS11)

#INCLUDES = /home/kyleperez/cppstuff/cpplibraries/eigen-3.3.8/
#-I $(INCLUDES)

# -g

$(EXEC): $(FILES)
	gcc -O3 \
	-o $(EXEC) \
	$(FILES) \
	-lm
	
clean:
	rm $(EXEC)
