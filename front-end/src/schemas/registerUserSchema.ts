import { z } from "zod"
import splitNames from "./functions/splitNames";

const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

export const registerUserSchema = z.object({
    cpf: z.string().regex(/^\d{11}$/, "O CPF deve conter 11 dígitos").nonempty("Campo obrigatório"),
    arquivo: z.object({
        caminho: z.string(),
        tipoArquivo: z.string().optional().nullable(),
        mimeType: z.string().optional().nullable(),
        tamanhoEmBytes: z.string().refine((size) => (size ? parseInt(size, 10) : 0) <= MAX_FILE_SIZE, `O tamanho máximo é 5MB.`),
        bucketArquivo: z.object({
            nome: z.string().optional().nullable(),
        }),
    }).optional().nullable(),
    nomeCompleto: z.string().nonempty("Campo obrigatório").refine(value => value.trim().includes(' '), {
        message: "Insira seu nome e sobrenome"
    }),
    nomeCompletoSocial: z.string().optional().nullable().transform(val => val === "" ? null : val).refine(value => {
        if (!value) return true;
        return value.trim().includes(' ');
    }, { message: "Insira seu nome e sobrenome social" }),
    sexo: z.enum(["Masculino", "Feminino", "Outro"], {
        error: () => ("Selecione um sexo"),
    }),
    dataNascimento: z.date({error: "Insira uma data válida"}).min(new Date("1900-01-01"), {message:"Data de nascimento inválida."}).transform(date => date.toISOString().split('T')[0]),
    email: z.email("E-mail inválido").nonempty("Campo obrigatório")
        .regex(/^[\w.-]+@(?:gmail\.com|outlook\.com|hotmail\.com|yahoo\.com)$/i, "O domínio do e-mail não é válido."),
    confirm_email: z.string(),
    telefone: z.string().nonempty("Campo obrigatório"),
    senha: z.string().min(8, "A senha deve ter no mínimo 8 caracteres.").max(32, "A senha deve ter no máximo 32 caracteres.").regex(/[A-Z]/, "Deve conter ao menos uma letra maiúscula.").regex(/[0-9]/, "Deve conter ao menos um dígito.").regex(/[^A-Za-z0-9]/, "Deve conter ao menos um caractere especial."),
    confirm_senha: z.string().nonempty("Campo obrigatório"),
    endereco: z.object({
        cep: z.string().length(8, "CEP deve ter 8 dígitos").nonempty("Campo obrigatório"),
        uf: z.string().nonempty("Campo obrigatório"),
        cidade: z.string().nonempty("Campo obrigatório"),
        bairro: z.string().nonempty("Campo obrigatório"),
        rua: z.string().nonempty("Campo obrigatório"),
        numero: z.string().nullable().transform((val) => val.trim() === "" ? "S/N" : val).optional(),
        complemento: z.string().max(30, "O complemento deve ter no máximo 30 caracteres").optional().nullable().transform(val => val === "" ? null : val),
    }),
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

export function buildUserPayload(values: RegisterUserFormData) {
  return {
    cpf: values.cpf,
    nomeCompleto: values.nomeCompleto,
    nomeSocialCompleto: values.nomeCompletoSocial || null,
    sexo: values.sexo,
    dataNascimento: values.dataNascimento,
    email: values.email,
    telefone: values.telefone,
    senha: values.senha,
    endereco: {
      cep: values.endereco.cep,
      uf: values.endereco.uf,
      cidade: values.endereco.cidade,
      bairro: values.endereco.bairro,
      logradouro: values.endereco.rua,
      numero: values.endereco.numero,
      complemento: values.endereco.complemento || null,
    }
  };
}