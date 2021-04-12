#ifndef SOLVER_OPTIONS_H
#define SOLVER_OPTIONS_H

// This file contains global variables that apply to the whole program

//---------------------------------------------------------------------
// Geometry

// Number of nodes
#define num_nodes  201

#define xlowbnd 0.
#define xhighbnd 1.

// Step sizse
#define dx ((xhighbnd - xlowbnd)/(double)(num_nodes-1))


//-----------------------------------------------------------------------------
// Particle properties

// Number of particles per cell
#define num_mps 4

// Total number of particles
#define num_particles (num_nodes - 1) * num_mps

// Do we have moving particles?
#define  moving_particles 0


//------------------------------------------------------------------------------
// Time

// Max time to run until
#define tmax 10.

// Number of steps to run over
#define tsteps 1000000

// Time stepsize
#define dt (tmax/(double)tsteps)




// DATA OPTIONS
#define node_filename "shake.csv"
#define mp_filename "shakempm_data.csv"

// How often to record data
#define record_frequency 5000


#define nd_titles_size  6
extern char* node_titles[nd_titles_size];

#define mp_titles_size 6
extern char* mp_titles[mp_titles_size];







#endif
