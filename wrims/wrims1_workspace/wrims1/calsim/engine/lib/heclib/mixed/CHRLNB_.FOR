      SUBROUTINE chrlnb_  (CLINE, ILAST, L_CLINE)
C
      CHARACTER CLINE  !![REFERENCE]
      INTEGER   ILAST  !![REFERENCE]
      INTEGER L_CLINE  !![VALUE]
C
C
      CALL CHRLNB (CLINE(1:L_CLINE), ILAST)
C
      RETURN
      END
