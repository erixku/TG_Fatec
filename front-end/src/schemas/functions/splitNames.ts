export default function splitNames(fullName: string, fullSocialName: string) {
    const nameParts = fullName.trim().split(" ");
    const nome = nameParts.shift() || "";
    const sobrenome = nameParts.join(" ");

    let nomeSocial = null;
    let sobrenomeSocial = null;
    if (fullSocialName && fullSocialName.trim().includes(' ')) {
        const socialNameParts = fullSocialName.trim().split(" ");
        nomeSocial = socialNameParts.shift() || null;
        sobrenomeSocial = socialNameParts.join(" ") || null;
    }

    return {
        nome, sobrenome, nomeSocial, sobrenomeSocial
    };
}