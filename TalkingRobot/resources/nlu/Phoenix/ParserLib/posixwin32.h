/* things we need to add for the PC.  */
#ifdef _WIN32
typedef char * caddr_t;
typedef unsigned long u_long;
typedef unsigned int u_int;
typedef unsigned short u_short;
typedef unsigned short u_int16;
typedef unsigned char u_char;
typedef float float32;
#define NDEBUG 1
#define M_PI 3.1415926535897932385E0
#define popen _popen
#define pclose _pclose
#endif

