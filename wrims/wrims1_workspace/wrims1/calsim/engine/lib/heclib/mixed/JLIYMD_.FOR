      INTEGER*4 FUNCTION jliymd_  (JULIAN, IYEAR, IMONTH, IDAY)
C
C     Interface routine to call HECLIB routine JLIYMD
C
      INTEGER JULIAN !![REFERENCE]
      INTEGER IYEAR  !![REFERENCE]
      INTEGER IMONTH !![REFERENCE]
      INTEGER IDAY   !![REFERENCE]
C
      jliymd_ = JLIYMD (JULIAN, IYEAR, IMONTH, IDAY)
C
      RETURN
      END
