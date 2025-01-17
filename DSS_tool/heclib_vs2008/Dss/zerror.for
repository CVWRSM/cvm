      SUBROUTINE ZERROR (IFLTAB, JERR, CSUB, ISTAT, IADD, CSTR1, NSTR1,
     * CSTR2, NSTR2)
C
C     Prints out error messages, then aborts via ZABORT.
C     Designed to handle errors common to several subroutines.
C     Written by Bill Charley, HEC, Feb 1990.
C
      INTEGER IFLTAB(*)
      CHARACTER CSUB*6
      CHARACTER CSTR1*(*), CSTR2*(*)
C
      INCLUDE 'zdsskz.h'
C
      INCLUDE 'zdssnz.h'
C
      INCLUDE 'zdssmz.h'
C
C
      IERR = JERR
C
      IF (JERR.EQ.5) THEN
C
C     Unopened file, or Invalid IFLTAB array (or, possibly
C     a corrupt IFLTAB array)
      IF ((IFLTAB(1).LT.4).OR.(IFLTAB(1).GT.8)) THEN
      WRITE (MUNIT,20) IFLTAB(1), CSUB
 20   FORMAT (/,' -----DSS***  Error:  DSS File Not Opened, or ',
     * 'Invalid IFLTAB Array ***',/,' IFLTAB(1):',I7,
     * ',   Called from Subroutine ',A,/)
      IERR = 70
      ELSE
      WRITE (MUNIT,40) IFLTAB(1), CSUB
 40   FORMAT (/,' -----DSS***  Error:  Incorrect DSS Version ',/,
     * ' Version:',I7,
     * ',   Called from Subroutine ',A,/)
      IERR = 300
      ENDIF
C
C
      ELSE IF (JERR.EQ.11) THEN
C
      N1 = MIN (LEN(CSTR1), NSTR1)
      N2 = MIN (LEN(CSTR2), NSTR2)
      IF (N2.LE.0) N2 = 1
      IF (N1.LE.0) N1 = 1
      WRITE (MUNIT,60) CSTR1(1:N1), NSTR1, NSTR2, CSTR2(1:N2)
 60   FORMAT (/,' -----DSS***  Error:  DSS Data Base File is Damaged',
     * ' ***',/,' A file pointer points to an invalid data area.',/,
     * ' Record: ',A,/,' Squeeze the file with DSSUTL to recover file'/,
     * ' Pathname Length supplied:',I4,'  Read from file:',I8,/,
     * ' Pathname read: ',A,/)
      CALL ZABORT (IFLTAB, IERR, CSUB, ISTAT, IADD, 'Damaged File')
      RETURN
C
      ELSE IF (JERR.EQ.41) THEN
C
C     WRITE (MUNIT, 80) CSTR1
C80   FORMAT (///,' ********** DSS  Error:  Disk Space',
C    * ' Exceeded  **********',/,
C    * ' Either the Owner of this File has no Disk Space Left,',/,
C    * ' or there is not enough Disk Space left on the disk pack,',/,
C    * ' or the file has reached the Maximum Size set by the System.',/
C    * /,' File: ',A)
C     WRITE (MUNIT, 81)
C81   FORMAT (/' Please Contact Your System Manager to Resolve',
C    * ' this problem.',/,' (You may need to Squeeze this file',
C    * ' with DSSUTL',/,' after the problem has been resolved.)',//)
C     CALL ZABORT (IFLTAB, 41, CSUB, ISTAT, IADD, 'Disk Space Exceeded')
C
      ELSE IF (JERR.EQ.100) THEN
C
      WRITE (MUNIT,100)
 100  FORMAT (/,' -----DSS***  Error:  DSS Data Base File is Damaged',
     * ' ***',/,' An invalid value was found in the main section of',
     * ' the file.',/,' Squeeze the file with DSSUTL to recover file'/)
      CALL ZABORT (IFLTAB, IERR, CSUB, 0, IADD, 'Bad KTABLE value')
      RETURN
C
C
      ELSE
C
      ENDIF
C
C
      CALL ZABORT (IFLTAB, IERR, CSUB, ISTAT, IADD, ' ')
      RETURN
C
      END
