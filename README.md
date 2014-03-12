carbon-clone
============

Java based multi-threaded file copier with error correction.

Starting very small and simple and then let it evolve, came about due to frustrating CRC errors when copying files from one location to another where some systems (Windows) or tools will simply abort the entire copy if an error is encountered. Sometimes it is deriable to have a file copied even with some errors.

Lots of to-do's:

- Add few other file correction algorithms such as Adler-32
- Use low level nio buffers to attempt error recovery through identified corrups bits.
- Implement a command line tool for copying
- Implement a GUI layer detached from the core, ideally utilizing the command line feature
- Allow user control of threads
- Ability to individually stop ongoing copy process
- Ability to disable any error checking for faster copying
- Safely add new copy tasks while there is an ongoing operation
- More to be added as it get's figured out
