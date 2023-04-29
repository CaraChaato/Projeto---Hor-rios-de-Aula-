package Projeto_HorariosDeAula.Code;

public class Professor{
    private String nome;
    private String cpf;
    private String formacao;
    private String email;
    private ArrayList<Integer> idTurma;

    /** 
     * O Construtor vai receber todas as variáveis e enviar para o banco de Dados
     * É um protópipo 
    */
    public Professor(String nome, String cpf, String formacao, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
    }
    
    public void Cadastrar(){

        if (buscarProfessor(this.cpf) != null) {
            try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){

                PreparedStatement pstmt = connection.prepareStatement("insert into professor(nome, cpf, formacao, email) values (?, ?, ?, ?)");
                pstmt.setString(1, nome);
                pstmt.setString(2, cpf);
                pstmt.setString(3, formacao);
                pstmt.setString(4, email);
                pstmt.executeUpdate();

            }
            catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
            }
        } else{
            System.out.println("Professor já cadastrado!");
        }

        

    }

    public Professor buscarProfessor(String cpf){
        
        try(Connection connection = PostgreSQLConnection.getInstance().getConnection()){

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT cpf from professor");

            while (rs.next()) {

                if (cpf == rs.getString(3)) {
                    return new Professor(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                } else{
                    return new Professor(null, null, null, null);
                }
                
            }

        }
        catch(java.sql.SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Metodo que reedita os dados de um professor
     * @param nome
     * @param cpf
     * @param formacao
     * @param email
     */
    public void editaProfessor(String nome, String cpf, String formacao, String email){
        this.nome = nome;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
    }



    @Override
    public String toString() {
        return "Professor [nome=" + nome + ", cpf=" + cpf + ", formacao=" + formacao + ", email=" + email + "]";
    }
}
