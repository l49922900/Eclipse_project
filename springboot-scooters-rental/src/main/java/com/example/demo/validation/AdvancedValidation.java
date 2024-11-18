package com.example.demo.validation;


//分組標記介面，為了優先驗證ScooterDto @NotNull
public interface AdvancedValidation extends BasicValidation {
	
	/*
	不需要在介面中撰寫具體邏輯： BasicValidation 和 AdvancedValidation 本身並不包含任何具體的驗證邏輯，
	它們僅僅是用來標識一組驗證規則。你只需在需要的地方引用這些分組，並在相應的地方使用 @Validated 標註來指定哪個分組的驗證規則應該執行。

	驗證邏輯的位置： 具體的驗證邏輯（例如 @NotNull, @Size, @Positive 等）應該放在你的 DTO 類別（如 ScooterDto）中，並使用這些分組來標識哪些驗證規則應該在不同情境下執行。例如：

	在 ScooterDto 類別中，你可以定義基本的驗證規則（如 @NotNull, @Size 等）並將它們標記為 BasicValidation。
	在 AdvancedValidation 中，你可以定義一些額外的驗證邏輯，例如進行複雜的驗證（如檢查某些欄位的範圍或業務邏輯）。
	 */

}
