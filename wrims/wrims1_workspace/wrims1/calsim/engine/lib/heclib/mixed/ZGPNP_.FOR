      SUBROUTINE ZGPNP_  (CLINE, CA, CB, CC, CD, CE, CF,
     *          NPARTS, L_CLINE, L_CA, L_CB, L_CC, L_CD, L_CE, L_CF)
C
C
      CHARACTER CLINE  !![REFERENCE]
      CHARACTER CA     !![REFERENCE]
      CHARACTER CB     !![REFERENCE]
      CHARACTER CC     !![REFERENCE]
      CHARACTER CD     !![REFERENCE]
      CHARACTER CE     !![REFERENCE]
      CHARACTER CF     !![REFERENCE]
      INTEGER   NPARTS !![REFERENCE]
      INTEGER L_CLINE  !![VALUE]
      INTEGER L_CA     !![VALUE]
      INTEGER L_CB     !![VALUE]
      INTEGER L_CC     !![VALUE]
      INTEGER L_CD     !![VALUE]
      INTEGER L_CE     !![VALUE]
      INTEGER L_CF     !![VALUE]
C
C
      CALL ZGPNP (CLINE(1:L_CLINE), CA(1:L_CA), CB(1:L_CB), CC(1:L_CC),
     *            CD(1:L_CD), CE(1:L_CE), CF(1:L_CF), NPARTS)
C
      RETURN
      END
