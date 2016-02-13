LZW Compressor

This program uses a modified version of an LZW based on the use of variable codewords, which will create a codebook of codewords sized
9-16 bits, based on the size of the file given. This program runs off the command line with the following commands:

compress: - 
	"n" for nothing mode: Once the codebook is full, the program continues using the full codebook
	"r" for reset mode: Once the codebook is full, the program empties the codebook and starts fresh
	"m" for monitor mode: Once the codebook is full, the program begins monitoring the compression ratio of the file. If it drops below a certain point, the program empties the codebook
decompress: +

Example of command line launch: (to compress foo.txt to foo.lzw and decompress it again)
java MyLZW - r < foo.txt > foo.lzw
java MyLZW + < foo.lzw > foo2.txt

Required Files:
BinaryStdIn.java
BinaryStdOut.java
myLZW.java* (main program)
Queue.java
StdIn.java
StdOut.java
TST.java

*indicates modified classes. All other classes are credited within the code