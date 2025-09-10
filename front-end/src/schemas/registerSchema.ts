import { z } from "zod"

export const registerSchema = z.object({
    name: z.string().nonempty("Campo obrigatório"),
    socialName: z.string().nullable(),
    cpf: z.string().regex(/^\d{11}$/, "O CPF deve conter 11 dígitos").nonempty("Campo obrigatório"),
    birthday: z.date({error: "Insira uma data válida"}).min(new Date("1900-01-01"), {message:"Data de nascimento inválida."}).transform(date=>date.toISOString().split('T')[0]),
    gender: z.enum(["Masculino", "Feminino"], {
        error: () => ("Selecione um sexo"),
    }),
    cep: z.string().length(8, "CEP deve ter 8 dígitos").nonempty("Campo obrigatório"),
    street: z.string().nonempty("Campo obrigatório"),
    homeNumber: z.string().nonempty("Campo obrigatório"),
    district: z.string().nonempty("Campo obrigatório"),
    city: z.string().nonempty("Campo obrigatório"),
    estate: z.string().nonempty("Campo obrigatório"),
    cellphone: z.string().nonempty("Campo obrigatório"),
    profilePicture: z.string().nullable(),
    email: z.email().nonempty("Campo obrigatório"),
    confirm_email: z.string(),
    password: z.string().nonempty("Campo obrigatório.").regex(/[A-Z]/, "Deve conter ao menos uma letra maiúscula.").regex(/[0-9]/, "Deve conter ao menos um dígito.").regex(/[^A-Za-z0-9]/, "Deve conter ao menos um caractere especial.").min(8, "Deve conter no mínimo 8 caracteres."),
    confirm_password: z.string(),
}).refine((fields) => fields.password === fields.confirm_password, {error: "As senhas não coincidem", path: ["confirm_password"]})
  .refine((fields) => fields.email === fields.confirm_email, {error: "Os e-mails não coincidem", path: ["confirm_email"]})

export type RegisterFormData = z.infer<typeof registerSchema>;