      SUBROUTINE ZRDPN (IUNIT, IPOS, INUMB, CPATH, NPATH)
C
C     Obsolete subroutine.  Use ZRDPAT instead.
C
C
      CHARACTER CPATH*(*), CTAG*8
      LOGICAL LEND
C
C
      CALL ZRDPAT (IUNIT, IPOS, INUMB, CTAG, CPATH, NPATH, LEND)
      IF (LEND) IPOS = 1000000
      RETURN
C
      END
