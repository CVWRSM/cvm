      SUBROUTINE ZRTEXT (IFLTAB, CPATH, IUNIT, IUHEAD, KUHEAD, NUHEAD,
     * LCCNTL, NLINES, ISTAT)
C
C     Retrieve Text data, writing the text to unit IUNIT
C     If LCCNTL is true, place a space before each line
C     to act as carriage control
C     NLINES is returned with the number of lines retrieved
C
C     Written by Bill Charley at HEC, 1990
C     Last modified, March 1995.  Added call to zrdbuf
C
      INTEGER IFLTAB(*), IUHEAD(*)
      PARAMETER (KLINE=160)
      CHARACTER CPATH*(*), CLINE*(KLINE), CLINE2*(KLINE)
      LOGICAL LCCNTL, LFOUND, LEND
C
      COMMON /WORDS/ IWORD(10)
C
      INCLUDE 'zdssmz.h'
C
      INCLUDE 'zdssts.h'
C
C
C
C     Check that IFLTAB is valid (e.g., the DSS file is open)
      IF (IFLTAB(1).NE.6) CALL ZERROR (IFLTAB, 5, 'ZRTEXT',
     * 0, IFLTAB, ' ', 0, ' ',0)
C
C
C
      ISTAT = 0
      CALL CHRLNB (CPATH, NPATH)
C
C     If a debug level is on, print out information
      IF (MLEVEL.GE.7) WRITE (MUNIT, 20) IUNIT, KUHEAD, LCCNTL,
     * CPATH(1:NPATH)
 20   FORMAT (T5,'----- Enter ZRTEXT  -----',/,
     * T11,'Write to Unit:',I5,'  User Header Dimension:',I5,
     * ',  Carriage Control:',L2,/,T11,'Pathname: ',A)
C
C
C
      JPOS = 1
      IPOS = 0
      NPOS = 0
      NLINES = 0
C
C
      CALL ZREADX (IFLTAB, CPATH, NBYTES, 1, N, ICHEAD, 0, N,
     * IUHEAD, KUHEAD, NUHEAD, ILBUFF, KLBUFF, NBUFF, 0, LFOUND)
C
      IF (.NOT.LFOUND) THEN
      ISTAT = -1
      GO TO 800
      ENDIF
C
      CALL ZINQIR (IFLTAB, 'TYPE', CLINE, JTYPE)
      IF (JTYPE.NE.300) GO TO 900
C
C     Do we need to call ZRDBUF (not enough array space)?
      IF (NBUFF.GT.KLBUFF) THEN
         LEND = .FALSE.
      ELSE
         LEND = .TRUE.
      ENDIF
C
C
 80   CONTINUE
      IF (.NOT.LEND) THEN
         CALL ZRDBUF (IFLTAB, CPATH, I, 0, N, ILBUFF, KLBUFF, NBUFF,
     *                LEND, 0, LFOUND)
      ENDIF
C
      NREAD = NBUFF * IWORD(2)
C
C
C     If the first character is a line feed, ignore it
      CALL HOLCHR (ILBUFF, 1, 1, CLINE, 1)
      IF (CLINE(1:1).EQ.CHAR(10)) JPOS = 2
C
 100  CONTINUE
      N = NBYTES - IPOS
      IF (N.GT.KLINE) N = KLINE
      NTOTAL = N + IPOS
      IF (NTOTAL.GT.NREAD) N = NREAD - IPOS
      IF (N.LE.0) THEN
         IF (LEND) THEN
            GO TO 800
         ELSE
            IPOS = 0
            JPOS = 1
            NBYTES = NBYTES - NREAD
            GO TO 80
         ENDIF
      ENDIF
      CALL HOLCHR (ILBUFF, IPOS+1, N, CLINE, 1)
      IPOS = IPOS + N
C
 120  CONTINUE
C     Look for a carriage return
      I = INDEX (CLINE(JPOS:N), CHAR(13))
C
      IF (I.EQ.0) THEN
      CLINE2 = CLINE(JPOS:)
      NPOS = N - JPOS + 1
      JPOS = 1
      GO TO 100
      ELSE
      KPOS = JPOS + I - 2
      ENDIF
C
      NLINES = NLINES + 1
      IF (NPOS.EQ.0) THEN
      IF (KPOS.LT.JPOS) THEN
C     Write out a blank line
      WRITE (IUNIT, *) ' '
      ELSE
      IF (LCCNTL) THEN
      WRITE (IUNIT,140, ERR=920, IOSTAT=JST) CLINE(JPOS:KPOS)
 140  FORMAT (1X,A,A)
      ELSE
      WRITE (IUNIT, 160, ERR=920, IOSTAT=JST) CLINE(JPOS:KPOS)
 160  FORMAT (A,A)
      ENDIF
      ENDIF
C
      ELSE
         IF (KPOS.LT.JPOS) THEN
            IF (LCCNTL) THEN
               WRITE (IUNIT,140, ERR=920, IOSTAT=JST) CLINE2(1:NPOS)
            ELSE
               WRITE (IUNIT, 160, ERR=920, IOSTAT=JST) CLINE2(1:NPOS)
            ENDIF
         ELSE
            IF (LCCNTL) THEN
               WRITE (IUNIT,140, ERR=920, IOSTAT=JST) CLINE2(1:NPOS),
     *                CLINE(JPOS:KPOS)
            ELSE
               WRITE (IUNIT, 160, ERR=920, IOSTAT=JST) CLINE2(1:NPOS),
     *                CLINE(JPOS:KPOS)
            ENDIF
         ENDIF
         NPOS = 0
      ENDIF
C
      JPOS = KPOS + 3
      IF (JPOS.GT.KLINE) THEN
      JPOS = JPOS - KLINE
      GO TO 100
      ELSE
      IF (JPOS.GT.N) THEN
         IF (LEND) THEN
            GO TO 800
         ELSE
            IPOS = 0
            JPOS = 1
            NBYTES = NBYTES - NREAD
            GO TO 80
         ENDIF
      ENDIF
      GO TO 120
      ENDIF
C
 800  CONTINUE
      IF (MLEVEL.GE.7) WRITE (MUNIT,820) NBUFF, NBYTES, NLINES, ISTAT
 820  FORMAT(T5,'----- Exit ZRTEXT, Number of data values read:',I5,/,
     * T11,'Number Bytes:',I6,',  Number Lines:',I5,',  Status:',I5)
C
      RETURN
C
C
 900  CONTINUE
      IF (MLEVEL.GE.1) WRITE (MUNIT,901) JTYPE, CPATH(1:NPATH)
 901  FORMAT (/,' *****DSS*** ZRTEXT:  ERROR - Record Not Identified',
     * ' as TEXT data.',/,' Data Type:',I5,/,' Pathname: ',A)
      ISTAT = -2
      GO TO 800
C
 920  CONTINUE
      IF (MLEVEL.GE.1) WRITE (MUNIT,921) IUNIT, JST, CPATH(1:NPATH)
 921  FORMAT (/,' *****DSS*** ZRTEXT:  ERROR During Write',
     * /,' Writing to Unit:',I5,',  Error:',I5,/,
     * ' Pathname: ',A,/)
      ISTAT = -4
      GO TO 800
C
      END
