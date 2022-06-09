#!/bin/bash

rawList=$(grep -r "public class" ../../src | awk '{print $3}')

preDefThings="
\lstdefinestyle{java}{
	language=java,
	%keywordstyle=\bfseries\color{green!40!black},
	backgroundcolor=\color{white!90!gray},
	keywordstyle=[1]\bfseries\color{orange},
	commentstyle=\itshape\color{blue!50!black},
	identifierstyle=\color{black},
	stringstyle=\color{red!80!black},
	keywords=[1]{
		abstract,
		assert,
		boolean,
		break,
		byte,
		case,
		catch,
		char,
		class,
		const,
		continue,
		default,
		do,
		double
		else,
		enum,
		extends,
		final,
		finally,
		float,
		for,
		if,
		implements,
		import,
		instnceof,
		int,
		interface,
		long,
		native,
		new,
		package,
		private,
		protected,
		public,
		return,
		short,
		static,
		strictfp,
		super,
		switch,
		synchronized,
		this,
		throw,
		transistent,
		try,
		void,
		volatile,
		while,
		true,
		false,
		null,
		},
	keywords=[2]{"


echo "$preDefThings"> ClassList.txt

for class in $rawList ; do
	echo $class, >>ClassList.txt
done

echo "	HelloWorld},
	keywordstyle=[2]\color{blue!60!green},
}" >> ClassList.txt