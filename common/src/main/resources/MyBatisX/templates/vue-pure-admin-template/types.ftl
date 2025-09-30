interface EditFormDTO {
isAddForm?: boolean;
formInline: ${tableClass.shortClassName}Type.AddDTO | ${tableClass.shortClassName}Type.UpdateDTO;
}

export type { EditFormDTO };
