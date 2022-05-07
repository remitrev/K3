package modeles;

public class PiecePyramide {
	private Piece piece;
	private Position pos;

	public PiecePyramide(Piece piece, Position position) {
		this.piece = piece;
		this.pos = position;
	}

	public Piece getPiece() {
		return piece;
	}

	public Position getPos() {
		return pos;
	}

	public String toString() {
		String res = "";
		res += piece.toString() + " et de position " + pos.toString();
		return res;
	}
	
	public boolean egal(PiecePyramide p) {
		Couleurs c2 = p.getPiece().getColor();
		Position p2 = p.getPos();
		int x2=p2.x;
		int y2=p2.y;
		Couleurs c1 = this.getPiece().getColor();
		Position p1 = this.getPos();
		int x1=p1.x;
		int y1=p1.y;
		return c1==c2 && x1==x2 && y1==y2;
	}

}
