/**
 */
package gov.ca.dwr.wresl.xtext.editor.wreslEditor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ConditionalTerm#getE1 <em>E1</em>}</li>
 *   <li>{@link ConditionalTerm#getE2 <em>E2</em>}</li>
 * </ul>
 * </p>
 *
 * @see WreslEditorPackage#getConditionalTerm()
 * @model
 * @generated
 */
public interface ConditionalTerm extends ConditionalUnary
{
  /**
   * Returns the value of the '<em><b>E1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>E1</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>E1</em>' containment reference.
   * @see #setE1(Expression)
   * @see WreslEditorPackage#getConditionalTerm_E1()
   * @model containment="true"
   * @generated
   */
  Expression getE1();

  /**
   * Sets the value of the '{@link ConditionalTerm#getE1 <em>E1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>E1</em>' containment reference.
   * @see #getE1()
   * @generated
   */
  void setE1(Expression value);

  /**
   * Returns the value of the '<em><b>E2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>E2</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>E2</em>' containment reference.
   * @see #setE2(Expression)
   * @see WreslEditorPackage#getConditionalTerm_E2()
   * @model containment="true"
   * @generated
   */
  Expression getE2();

  /**
   * Sets the value of the '{@link ConditionalTerm#getE2 <em>E2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>E2</em>' containment reference.
   * @see #getE2()
   * @generated
   */
  void setE2(Expression value);

} // ConditionalTerm
