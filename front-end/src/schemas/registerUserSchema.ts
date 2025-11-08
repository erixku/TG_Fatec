import { z } from "zod"
import splitNames from "./functions/splitNames";

export const registerUserSchema = z.object({
    cpf: z.string().regex(/^\d{11}$/, "O CPF deve conter 11 dígitos").nonempty("Campo obrigatório"),
    nomeCompleto: z.string().nonempty("Campo obrigatório").refine(value => value.trim().includes(' '), {
        message: "Insira seu nome e sobrenome"
    }),
    nomeCompletoSocial: z.string().nullable().refine(value => {
        if (!value) return true;
        return value.trim().includes(' ');
    }, { message: "Insira seu nome e sobrenome social" }).optional(),
    sexo: z.enum(["Masculino", "Feminino", "Outro"], {
        error: () => ("Selecione um sexo"),
    }),
    dataNascimento: z.date({error: "Insira uma data válida"}).min(new Date("1900-01-01"), {message:"Data de nascimento inválida."}).transform(date=>date.toISOString().split('T')[0]),
    email: z.email("E-mail inválido").nonempty("Campo obrigatório")
        .regex(/^[\w.-]+@(?:gmail\.com|outlook\.com|hotmail\.com|yahoo\.com)$/i, "O domínio do e-mail não é válido."),
    confirm_email: z.string(),
    telefone: z.string().nonempty("Campo obrigatório"),
    senha: z.string().min(1, "Campo obrigatório").nonempty("Campo obrigatório.").regex(/[A-Z]/, "Deve conter ao menos uma letra maiúscula.").regex(/[0-9]/, "Deve conter ao menos um dígito.").regex(/[^A-Za-z0-9]/, "Deve conter ao menos um caractere especial.").min(8, "Deve conter no mínimo 8 caracteres.").max(20, "Deve conter no máximo 32 caracteres."),
    confirm_senha: z.string().min(1, "Campo obrigatório"),
    endereco: z.object({
        cep: z.string().length(8, "CEP deve ter 8 dígitos").nonempty("Campo obrigatório"),
        uf: z.string().nonempty("Campo obrigatório"),
        cidade: z.string().nonempty("Campo obrigatório"),
        bairro: z.string().nonempty("Campo obrigatório"),
        rua: z.string().nonempty("Campo obrigatório"),
        numero: z.string().nullable().transform((val) => val.trim() === "" ? "S/N" : val).optional(),
        complemento: z.string().max(30, "O complemento deve ter no máximo 30 caracteres").nullable().optional(),
    }),
    arquivo: z.object({
        caminho: z.string().nullable(),
        tipoArquivo: z.string().nullable(),
        mimeType: z.string().nullable(),
        tamanhoEmBytes: z.string().nullable(),
        bucketArquivo: z.object({
            nome: z.string().nullable()
        })
    }).optional(),
}).refine((fields) => fields.senha === fields.confirm_senha, { message: "As senhas não coincidem", path: ["confirm_senha"] })
  .refine((fields) => fields.email === fields.confirm_email, { message: "Os e-mails não coincidem", path: ["confirm_email"] })
  .transform((data) => {
    const nameParts = splitNames(data.nomeCompleto, data.nomeCompletoSocial || "");
    return{
        ...data,
        ...nameParts
    }
});

export type RegisterUserFormData = z.infer<typeof registerUserSchema>;